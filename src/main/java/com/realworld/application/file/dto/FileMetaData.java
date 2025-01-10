package com.realworld.application.file.dto;

import com.realworld.common.holder.uuid.UUIDHolder;
import com.realworld.infrastructure.image.ResizedImage;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileMetaData {

    private final String targetDirectory;
    private final String name;
    private final String contentType;
    private final long size;

    @Builder
    private FileMetaData(String targetDirectory, String name, long size, String contentType) {
        this.targetDirectory = targetDirectory;
        this.name = name;
        this.size = size;
        this.contentType = contentType;
    }

    public static FileMetaData create(String targetDirectory, UUIDHolder uuidHolder, ResizedImage resizedImage) {
        return FileMetaData.builder()
                .targetDirectory(targetDirectory)
                .name(uuidHolder.generate() + "." + resizedImage.getExtension())
                .contentType("image/" + resizedImage.getExtension())
                .size(resizedImage.getSize())
                .build();
    }

    public static FileMetaData create(MultipartFile file, String targetDirectory, UUIDHolder uuidHolder) {
        String originalFilename = FilenameUtils.getName(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(originalFilename);

        return FileMetaData.builder()
                .targetDirectory(targetDirectory)
                .name(uuidHolder.generate() + "." + extension)
                .contentType("image/" + extension)
                .size(file.getSize())
                .build();
    }

}
