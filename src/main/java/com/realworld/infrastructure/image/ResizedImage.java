package com.realworld.infrastructure.image;

import com.realworld.common.exception.custom.CustomImageExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import lombok.Getter;

import java.io.InputStream;
import java.util.Objects;

@Getter
public class ResizedImage implements AutoCloseable {

    private final InputStream inputStream;
    private final String imageFormat;
    private final long size;

    private ResizedImage(final InputStream inputStream, final String imageFormat, final long size) {
        this.inputStream = inputStream;
        this.imageFormat = imageFormat;
        this.size = size;
        notNullParameters(inputStream, imageFormat, size);
    }

    private void notNullParameters(final InputStream inputStream, final String imageFormat, final long size) {
        if (Objects.isNull(inputStream) || Objects.isNull(imageFormat) || size <= 0) {
            throw new CustomImageExceptionHandler(ErrorCode.FILE_IMAGE_PROCESSING_ERROR);
        }
    }

    public static ResizedImage of(final InputStream inputStream, final String imageFormat, final long size) {
        return new ResizedImage(inputStream, imageFormat, size);
    }

    @Override
    public void close() throws Exception {
        if (inputStream != null) {
            inputStream.close();
        }
    }

}
