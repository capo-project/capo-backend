package com.realworld.feature.file;

import lombok.Getter;

@Getter
public class FileDetails {

    private final String name;
    private final String contentType;
    private final long size;

    private FileDetails(String name, String contentType, long size) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
    }

    public static FileDetails of(String name, String contentType, long size) {
        return new FileDetails(name, contentType, size);
    }

}
