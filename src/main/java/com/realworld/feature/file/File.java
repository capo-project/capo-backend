package com.realworld.feature.file;

import com.realworld.common.type.file.FileMetaData;
import lombok.Builder;
import lombok.Getter;

@Getter
public class File {

    private final String name;
    private final String contentType;
    private final long size;
    private final String url;

    @Builder
    private File(String name, String contentType, long size, String url) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.url = url;
    }

    public static File create(FileMetaData metaData, String url) {
        return File.builder()
                .name(metaData.getName())
                .contentType(metaData.getContentType())
                .size(metaData.getSize())
                .url(url)
                .build();
    }

}
