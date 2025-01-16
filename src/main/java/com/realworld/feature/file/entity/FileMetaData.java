package com.realworld.feature.file.entity;

import com.realworld.common.exception.CustomFileExceptionHandler;
import com.realworld.common.holder.uuid.UUIDHolder;
import com.realworld.common.response.code.ExceptionResponseCode;
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
    private FileMetaData(String directory, FileDetails details) {
        this.directory = directory;
        this.details = details;
    }

    public static FileMetaData fromResizedImage(String destinationDirectory, ResizedImage resizedImage, UUIDHolder uuidHolder) {
        notNullParameters(resizedImage, destinationDirectory, uuidHolder);

        String generatedName = FileFormat.IMAGE.generateFileName(uuidHolder.generate(), resizedImage.getImageFormat());
        String generatedContentType = FileFormat.IMAGE.generateContentType(resizedImage.getImageFormat());
        long size = resizedImage.getSize();

        FileDetails details = FileDetails.of(generatedName, generatedContentType, size);

        return FileMetaData.builder()
                .directory(destinationDirectory)
                .details(details)
                .build();
    }

    private static void notNullParameters(ResizedImage resizedImage, String destinationDirectory, UUIDHolder uuidHolder) {
        if (Objects.isNull(destinationDirectory) || Objects.isNull(uuidHolder) || Objects.isNull(resizedImage) || resizedImage.getSize() <= 0) {
            throw new CustomFileExceptionHandler(ExceptionResponseCode.FILE_PROCESSING_ERROR);
        }
    }

    public static FileMetaData fromMultipartFile(String destinationDirectory, MultipartFile file, UUIDHolder uuidHolder) {
        notNullParameters(file, destinationDirectory, uuidHolder);

        String originalFilename = FilenameUtils.getName(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(originalFilename);
        String generatedName = FileFormat.IMAGE.generateFileName(uuidHolder.generate(), extension);
        String generatedContentType = FileFormat.IMAGE.generateContentType(extension);
        long size = file.getSize();

        FileDetails details = FileDetails.of(generatedName, generatedContentType, size);

        return FileMetaData.builder()
                .directory(destinationDirectory)
                .details(details)
                .build();
    }

    private static void notNullParameters(MultipartFile file, String destinationDirectory, UUIDHolder uuidHolder) {
        if (Objects.isNull(destinationDirectory) || Objects.isNull(uuidHolder) || Objects.isNull(file) || file.getSize() <= 0) {
            throw new CustomFileExceptionHandler(ExceptionResponseCode.FILE_PROCESSING_ERROR);
        }
    }

}
