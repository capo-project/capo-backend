package com.realworld.feature.file.entity;

import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import lombok.Getter;

import java.util.Objects;

@Getter
public class FileDetails {

    private final String name;
    private final String contentType;
    private final long size;

    private FileDetails(final String name, final String contentType, final long size) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        notNullParameters(name, contentType, size);
    }

    private void notNullParameters(final String name, final String contentType, final long size) {
        if (Objects.isNull(name) || Objects.isNull(contentType) || size <= 0) {
            throw new CustomFileExceptionHandler(ErrorCode.FILE_PROCESSING_ERROR);
        }
    }

    public static FileDetails of(final String name, final String contentType, final long size) {
        return new FileDetails(name, contentType, size);
    }

}
