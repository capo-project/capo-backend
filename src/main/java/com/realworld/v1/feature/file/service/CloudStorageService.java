package com.realworld.feature.file.service;

import com.realworld.feature.file.domain.File;
import com.realworld.feature.file.entity.FileJpaEntity;
import com.realworld.feature.file.exception.FileExceptionHandler;
import com.realworld.feature.file.repository.FileRepository;
import com.realworld.feature.image.ThumbnailImageGenerator;
import com.realworld.global.code.ErrorCode;
import com.realworld.infra.aws.AwsService;
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
    public File upload(InputStream inputStream, String userId, File file) {
        file.updateId(fileNameGenerator.createFileId());

        try {
            if (file.getContentType().contains("image")) {
                BufferedImage inputImage = ImageIO.read(inputStream);

                // 썸네일 이미지 저장
                try {
                    BufferedImage thumbBufferedImage = thumbnailImageGenerator.thumbnailBufferedImage(inputImage);

                    try (ByteArrayOutputStream thumbOs = new ByteArrayOutputStream()) {
                        ImageIO.write(thumbBufferedImage, ThumbnailImageGenerator.THUMBNAIL_IMAGE_EXTENSION, thumbOs);

                        try (ByteArrayInputStream thumbIns = new ByteArrayInputStream(thumbOs.toByteArray())) {
                            awsService.uploadS3Bucket(thumbIns,
                                    ThumbnailImageGenerator.THUMBNAIL_PREFIX + file.getId(),
                                    thumbOs.size(),
                                    "image/" + ThumbnailImageGenerator.THUMBNAIL_IMAGE_EXTENSION);
                        }
                    }
                    file.updateHasThumbnail(true);
                } catch (Exception e) {
                    log.error("Error create thumbnail image", e);
                    file.updateHasThumbnail(false);
                }

                // 원본 이미지 저장
                try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                    ImageIO.write(inputImage, file.getExtension(), os);

                    try (ByteArrayInputStream ins = new ByteArrayInputStream(os.toByteArray())) {
                        String filePath = awsService.uploadS3Bucket(ins, String.valueOf(file.getId()),
                                os.size(), file.getContentType());
                        file.updatePath(filePath);
                    }
                }
            } else {
                String filePath = awsService.uploadS3Bucket(inputStream, String.valueOf(file.getId()), file.getSize(), file.getContentType());
                file.updatePath(filePath);
                file.updateHasThumbnail(false);
            }
        } catch (Exception e) {
            log.error("ERROR!!! upload file", e);
            throw new FileExceptionHandler("파일 업로드 중 오류가 발생했습니다.", ErrorCode.BAD_REQUEST_ERROR);
        }

        fileRepository.save(file.toEntity());

        return file;
    }

    @Override
    public String getFile(String id) {
        return awsService.getFile(id);
    }

    @Override
    public void delete(String userId, String fileId) {
        Optional<FileJpaEntity> fileJpaEntity = fileRepository.findById(UUID.fromString(fileId));

        if (fileJpaEntity.isPresent()) {
            File file = File.builder()
                    .id(fileJpaEntity.get().getId())
                    .name(fileJpaEntity.get().getName())
                    .extension(fileJpaEntity.get().getExtension())
                    .size(fileJpaEntity.get().getSize())
                    .path(fileJpaEntity.get().getPath())
                    .hasThumbnail(fileJpaEntity.get().isHasThumbnail())
                    .build();

            awsService.deleteS3Bucket(fileId);

            if (file.isHasThumbnail()) {
                awsService.deleteS3Bucket(ThumbnailImageGenerator.THUMBNAIL_PREFIX + fileId);
            }

            fileRepository.delete(fileJpaEntity.get());
        }
    }
}
