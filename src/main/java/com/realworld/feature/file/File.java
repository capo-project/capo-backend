package com.realworld.feature.file;

import lombok.Getter;

@Getter
public class File {

    private final String name;
    private final String contentType;
    private final long size;
    private final String url;

    private File(String name, String contentType, long size, String url) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.url = url;
    }

    public static File of(String name, String contentType, long size, String url) {
        return new File(name, contentType, size, url);
    }

}
