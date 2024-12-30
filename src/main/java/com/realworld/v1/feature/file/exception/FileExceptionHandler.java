package com.realworld.feature.file.exception;

import com.realworld.global.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FileExceptionHandler extends RuntimeException{

    private final ErrorCode errorCode;

    @Builder
    public FileExceptionHandler(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public FileExceptionHandler(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
