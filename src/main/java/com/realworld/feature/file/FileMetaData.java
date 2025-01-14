package com.realworld.feature.file;

import com.realworld.common.holder.uuid.UUIDHolder;
import com.realworld.common.type.file.FileFormat;
import com.realworld.infrastructure.image.ResizedImage;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileMetaData {

    private final String directory;
    private final FileDetails details;

    @Builder
    private FileMetaData(String directory, FileDetails details) {
        this.directory = directory;
        this.details = details;
    }

    public static FileMetaData create(String destinationDirectory, UUIDHolder uuidHolder, ResizedImage resizedImage) {
        String generatedName = FileFormat.IMAGE.generateFileName(uuidHolder.generate(), resizedImage.getImageFormat());
        String generatedContentType = FileFormat.IMAGE.generateContentType(resizedImage.getImageFormat());
        long size = resizedImage.getSize();

        FileDetails details = FileDetails.of(generatedName, generatedContentType, size);

        return FileMetaData.builder()
                .directory(destinationDirectory)
                .details(details)
                .build();
    }

    public static FileMetaData create(String destinationDirectory, MultipartFile file, UUIDHolder uuidHolder) {
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

}
