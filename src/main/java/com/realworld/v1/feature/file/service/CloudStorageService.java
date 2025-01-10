package com.realworld.v1.feature.file.service;


import com.realworld.v1.feature.file.domain.FileV1;
import com.realworld.v1.feature.file.entity.FileJpaEntity;
import com.realworld.v1.feature.file.exception.FileExceptionHandler;
import com.realworld.v1.feature.file.repository.FileRepository;
import com.realworld.v1.feature.image.ThumbnailImageGenerator;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.infra.aws.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Primary
@RequiredArgsConstructor
public class CloudStorageService implements StorageService {
    private final FileNameGenerator fileNameGenerator;
    private final ThumbnailImageGenerator thumbnailImageGenerator;
    private final FileRepository fileRepository;
    private final AwsService awsService;

    @Override
    public FileV1 upload(InputStream inputStream, String userId, FileV1 fileV1) {
        fileV1.updateId(fileNameGenerator.createFileId());

        try {
            if (fileV1.getContentType().contains("image")) {
                BufferedImage inputImage = ImageIO.read(inputStream);

                // 썸네일 이미지 저장
                try {
                    BufferedImage thumbBufferedImage = thumbnailImageGenerator.thumbnailBufferedImage(inputImage);

                    try (ByteArrayOutputStream thumbOs = new ByteArrayOutputStream()) {
                        ImageIO.write(thumbBufferedImage, ThumbnailImageGenerator.THUMBNAIL_IMAGE_EXTENSION, thumbOs);

                        try (ByteArrayInputStream thumbIns = new ByteArrayInputStream(thumbOs.toByteArray())) {
                            awsService.uploadS3Bucket(thumbIns,
                                    ThumbnailImageGenerator.THUMBNAIL_PREFIX + fileV1.getId(),
                                    thumbOs.size(),
                                    "image/" + ThumbnailImageGenerator.THUMBNAIL_IMAGE_EXTENSION);
                        }
                    }
                    fileV1.updateHasThumbnail(true);
                } catch (Exception e) {
                    log.error("Error create thumbnail image", e);
                    fileV1.updateHasThumbnail(false);
                }

                // 원본 이미지 저장
                try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                    ImageIO.write(inputImage, fileV1.getExtension(), os);

                    try (ByteArrayInputStream ins = new ByteArrayInputStream(os.toByteArray())) {
                        String filePath = awsService.uploadS3Bucket(ins, String.valueOf(fileV1.getId()),
                                os.size(), fileV1.getContentType());
                        fileV1.updatePath(filePath);
                    }
                }
            } else {
                String filePath = awsService.uploadS3Bucket(inputStream, String.valueOf(fileV1.getId()), fileV1.getSize(), fileV1.getContentType());
                fileV1.updatePath(filePath);
                fileV1.updateHasThumbnail(false);
            }
        } catch (Exception e) {
            log.error("ERROR!!! upload file", e);
            throw new FileExceptionHandler("파일 업로드 중 오류가 발생했습니다.", ErrorCode.BAD_REQUEST_ERROR);
        }

        fileRepository.save(fileV1.toEntity());

        return fileV1;
    }

    @Override
    public String getFile(String id) {
        return awsService.getFile(id);
    }

    @Override
    public void delete(String userId, String fileId) {
        Optional<FileJpaEntity> fileJpaEntity = fileRepository.findById(UUID.fromString(fileId));

        if (fileJpaEntity.isPresent()) {
            FileV1 fileV1 = FileV1.builder()
                    .id(fileJpaEntity.get().getId())
                    .name(fileJpaEntity.get().getName())
                    .extension(fileJpaEntity.get().getExtension())
                    .size(fileJpaEntity.get().getSize())
                    .path(fileJpaEntity.get().getPath())
                    .hasThumbnail(fileJpaEntity.get().isHasThumbnail())
                    .build();

            awsService.deleteS3Bucket(fileId);

            if (fileV1.isHasThumbnail()) {
                awsService.deleteS3Bucket(ThumbnailImageGenerator.THUMBNAIL_PREFIX + fileId);
            }

            fileRepository.delete(fileJpaEntity.get());
        }
    }
}
