package com.realworld.feature.file.entity;

import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.holder.uuid.UUIDHolder;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.common.type.file.FileFormat;
import com.realworld.infrastructure.image.ResizedImage;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Getter
public class FileMetaData {

    private final String directory;
    private final FileDetails details;

    @Builder
    private FileMetaData(final String directory, final FileDetails details) {
        this.directory = directory;
        this.details = details;
    }

    public static FileMetaData fromResizedImage(final String targetDir, final ResizedImage resizedImage, final UUIDHolder uuidHolder) {
        notNullParameters(resizedImage, targetDir, uuidHolder);

        String generatedName = FileFormat.IMAGE.generateFileName(uuidHolder.generate(), resizedImage.getImageFormat());
        String generatedContentType = FileFormat.IMAGE.generateContentType(resizedImage.getImageFormat());
        long size = resizedImage.getSize();

        FileDetails details = FileDetails.of(generatedName, generatedContentType, size);

        return FileMetaData.builder()
                .directory(targetDir)
                .details(details)
                .build();
    }

    private static void notNullParameters(final ResizedImage resizedImage, final String targetDir, final UUIDHolder uuidHolder) {
        if (Objects.isNull(targetDir) || Objects.isNull(uuidHolder) || Objects.isNull(resizedImage) || resizedImage.getSize() <= 0) {
            throw new CustomFileExceptionHandler(ErrorCode.FILE_PROCESSING_ERROR);
        }
    }

    public static FileMetaData fromMultipartFile(final String targetDir, final MultipartFile file, final UUIDHolder uuidHolder) {
        notNullParameters(file, targetDir, uuidHolder);

        String originalFilename = FilenameUtils.getName(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(originalFilename);
        String generatedName = FileFormat.IMAGE.generateFileName(uuidHolder.generate(), extension);
        String generatedContentType = FileFormat.IMAGE.generateContentType(extension);
        long size = file.getSize();

        FileDetails details = FileDetails.of(generatedName, generatedContentType, size);

        return FileMetaData.builder()
                .directory(targetDir)
                .details(details)
                .build();
    }

    private static void notNullParameters(final MultipartFile file, final String targetDir, final UUIDHolder uuidHolder) {
        if (Objects.isNull(targetDir) || Objects.isNull(uuidHolder) || Objects.isNull(file) || file.getSize() <= 0) {
            throw new CustomFileExceptionHandler(ErrorCode.FILE_PROCESSING_ERROR);
        }
    }

}
