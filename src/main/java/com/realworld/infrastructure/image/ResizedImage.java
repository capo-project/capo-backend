package com.realworld.infrastructure.image;

import com.realworld.common.exception.CustomImageExceptionHandler;
import com.realworld.common.response.code.ExceptionResponseCode;
import lombok.Getter;

import java.io.InputStream;
import java.util.Objects;

@Getter
public class ResizedImage implements AutoCloseable {

    private final InputStream inputStream;
    private final String imageFormat;
    private final long size;

    private ResizedImage(InputStream inputStream, String imageFormat, long size) {
        this.inputStream = inputStream;
        this.imageFormat = imageFormat;
        this.size = size;
        notNullParameters(inputStream, imageFormat, size);
    }

    private void notNullParameters(InputStream inputStream, String imageFormat, long size) {
        if (Objects.isNull(inputStream) || Objects.isNull(imageFormat) || size <= 0) {
            throw new CustomImageExceptionHandler(ExceptionResponseCode.FILE_IMAGE_PROCESSING_ERROR);
        }
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
