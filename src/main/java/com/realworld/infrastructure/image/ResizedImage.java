package com.realworld.infrastructure.image;

import lombok.Getter;

import java.io.InputStream;

@Getter
public class ResizedImage implements AutoCloseable {

    private final String extension;
    private final long size;
    private final InputStream inputStream;

    private ResizedImage(String extension, long size, InputStream inputStream) {
        this.extension = extension;
        this.size = size;
        this.inputStream = inputStream;
    }

    public static ResizedImage of(String extension, long size, InputStream inputStream) {
        return new ResizedImage(extension, size, inputStream);
    }

    @Override
    public void close() throws Exception {
        if (inputStream != null) {
            inputStream.close();
        }
    }

}
