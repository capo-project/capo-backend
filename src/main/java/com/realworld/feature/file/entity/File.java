package com.realworld.feature.file.entity;

import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import lombok.Getter;

import java.util.Objects;

@Getter
public class File {

    private final FileDetails details;
    private final String url;

    private File(final FileDetails details, final String url) {
        this.details = details;
        this.url = url;
        notNullParameters(details, url);
    }

    private void notNullParameters(final FileDetails details, final String url) {
        if (Objects.isNull(details) || Objects.isNull(url)) {
            throw new CustomFileExceptionHandler(ErrorCode.FILE_PROCESSING_ERROR);
        }
    }

    public static File create(final FileDetails details, final String url) {
        return new File(details, url);
    }

}
