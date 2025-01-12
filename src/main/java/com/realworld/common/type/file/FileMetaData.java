package com.realworld.common.type.file;

import com.realworld.common.holder.uuid.UUIDHolder;
import com.realworld.infrastructure.image.ResizedImage;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileMetaData {

    private final String directory;
    private final String name;
    private final String contentType;
    private final long size;

    @Builder
    private FileMetaData(String directory, String name, String contentType, long size) {
        this.directory = directory;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
    }

    public static FileMetaData create(String destinationDirectory, UUIDHolder uuidHolder, ResizedImage resizedImage) {
        return FileMetaData.builder()
                .directory(destinationDirectory)
                .name(FileFormat.IMAGE.generateFileName(uuidHolder.generate(), resizedImage.getImageFormat()))
                .contentType(FileFormat.IMAGE.generateContentType(resizedImage.getImageFormat()))
                .size(resizedImage.getSize())
                .build();
    }

    public static FileMetaData create(String destinationDirectory, MultipartFile file, UUIDHolder uuidHolder) {
        String originalFilename = FilenameUtils.getName(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(originalFilename);

        return FileMetaData.builder()
                .directory(destinationDirectory)
                .name(FileFormat.IMAGE.generateFileName(uuidHolder.generate(), extension))
                .contentType(FileFormat.IMAGE.generateContentType(extension))
                .size(file.getSize())
                .build();
    }

}
