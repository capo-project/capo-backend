package com.realworld.feature.file.service;

import com.realworld.feature.file.domain.File;
import com.realworld.feature.file.entity.FileJpaEntity;
import com.realworld.feature.file.exception.FileExceptionHandler;
import com.realworld.feature.file.repository.FileRepository;
import com.realworld.feature.image.ThumbnailImageGenerator;
import com.realworld.global.code.ErrorCode;
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
    public File upload(InputStream inputStream, String userId, File file) {
        file.updateId(fileNameGenerator.createFileId());

        String savedFileName = file.getId() + FilenameUtils.EXTENSION_SEPARATOR_STR + file.getExtension();
        file.updatePath(filePath + java.io.File.separator + savedFileName);

        try {
            if (file.getContentType().contains("image")) {
                BufferedImage inputImage = ImageIO.read(inputStream);

                try {
                    BufferedImage thumbBufferedImage = thumbnailImageGenerator.thumbnailBufferedImage(inputImage);
                    String thumbnailFileName = thumbnailImageGenerator.createThumbnailFileName(file.getId().toString());

                    java.io.File thumbnailFile = new java.io.File(filePath, thumbnailFileName);
                    ImageIO.write(thumbBufferedImage, ThumbnailImageGenerator.THUMBNAIL_IMAGE_EXTENSION, thumbnailFile);
                } catch (Exception e) {
                    log.error("ERROR!!! create thumbnail image", e);
                }

                file.updateHasThumbnail(true);
                ImageIO.write(inputImage, file.getExtension(), new java.io.File(filePath, savedFileName));
            } else {
                FileUtils.copyInputStreamToFile(inputStream, new java.io.File(filePath, savedFileName));
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
