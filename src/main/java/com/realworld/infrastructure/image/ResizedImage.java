package com.realworld.infrastructure.image;

import lombok.Getter;

import java.io.InputStream;

@Getter
public class ResizedImage implements AutoCloseable {

    private final InputStream inputStream;
    private final String imageFormat;
    private final long size;

    private ResizedImage(InputStream inputStream, String imageFormat, long size) {
        this.inputStream = inputStream;
        this.imageFormat = imageFormat;
        this.size = size;
    }

    public static ResizedImage of(InputStream inputStream, String imageFormat, long size) {
        return new ResizedImage(inputStream, imageFormat, size);
    }

    @Override
    public void close() throws Exception {
        if (inputStream != null) {
            inputStream.close();
        }
    }

}
