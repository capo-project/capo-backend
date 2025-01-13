package com.realworld.v1.feature.file.service;


import com.realworld.v1.feature.file.domain.FileV1;
import com.realworld.v1.feature.file.entity.FileJpaEntity;
import com.realworld.v1.feature.file.exception.FileExceptionHandler;
import com.realworld.v1.feature.file.repository.FileRepository;
import com.realworld.v1.feature.image.ThumbnailImageGenerator;
import com.realworld.v1.global.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    final private FileRepository fileRepository;
    final private FileNameGenerator fileNameGenerator;
    final private ThumbnailImageGenerator thumbnailImageGenerator;

    @Value("${file.path}")
    private String filePath;

    @Override
    public FileV1 upload(InputStream inputStream, String userId, FileV1 fileV1) {
        fileV1.updateId(fileNameGenerator.createFileId());

        String savedFileName = fileV1.getId() + FilenameUtils.EXTENSION_SEPARATOR_STR + fileV1.getExtension();
        fileV1.updatePath(filePath + java.io.File.separator + savedFileName);

        try {
            if (fileV1.getContentType().contains("image")) {
                BufferedImage inputImage = ImageIO.read(inputStream);

                try {
                    BufferedImage thumbBufferedImage = thumbnailImageGenerator.thumbnailBufferedImage(inputImage);
                    String thumbnailFileName = thumbnailImageGenerator.createThumbnailFileName(fileV1.getId().toString());

                    java.io.File thumbnailFile = new java.io.File(filePath, thumbnailFileName);
                    ImageIO.write(thumbBufferedImage, ThumbnailImageGenerator.THUMBNAIL_IMAGE_EXTENSION, thumbnailFile);
                } catch (Exception e) {
                    log.error("ERROR!!! create thumbnail image", e);
                }

                fileV1.updateHasThumbnail(true);
                ImageIO.write(inputImage, fileV1.getExtension(), new java.io.File(filePath, savedFileName));
            } else {
                FileUtils.copyInputStreamToFile(inputStream, new java.io.File(filePath, savedFileName));
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
    public void delete(String userId, String fileId) {
        Optional<FileJpaEntity> fileJpaEntity = fileRepository.findById(UUID.fromString(fileId));

        // TODO: 파일 삭제는 직접ㅎㅎ
        fileJpaEntity.ifPresent(fileRepository::delete);
    }

    @Override
    public String getFile(String id) {
        // 파일 가져오기
        return null;
    }
}
