package com.realworld.feature.file;

import com.realworld.common.exception.CustomFileExceptionHandler;
import com.realworld.common.response.code.ExceptionResponseCode;
import lombok.Getter;

import java.util.Objects;

@Getter
public class FileDetails {

    private final String name;
    private final String contentType;
    private final long size;

    private FileDetails(String name, String contentType, long size) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        notNullParameters(name, contentType, size);
    }

    private void notNullParameters(String name, String contentType, long size) {
        if (Objects.isNull(name) || Objects.isNull(contentType) || size <= 0) {
            throw new CustomFileExceptionHandler(ExceptionResponseCode.FILE_PROCESSING_ERROR);
        }
    }

    public static FileDetails of(String name, String contentType, long size) {
        return new FileDetails(name, contentType, size);
    }

}
