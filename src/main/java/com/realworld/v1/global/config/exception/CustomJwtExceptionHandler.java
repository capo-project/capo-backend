package com.realworld.v1.global.config.exception;

import com.realworld.v1.global.code.ErrorCode;
import lombok.Getter;

@Getter
public class CustomJwtExceptionHandler extends RuntimeException{
    ErrorCode errorCode;

    public CustomJwtExceptionHandler(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public CustomJwtExceptionHandler(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
