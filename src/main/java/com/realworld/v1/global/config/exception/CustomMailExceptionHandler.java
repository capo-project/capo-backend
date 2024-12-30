package com.realworld.v1.global.config.exception;

import com.realworld.v1.global.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomMailExceptionHandler extends  RuntimeException{
    private final ErrorCode errorCode;

    @Builder
    public CustomMailExceptionHandler(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public CustomMailExceptionHandler(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
