package com.realworld.v1.global.config.exception;

import com.realworld.v1.global.code.ErrorCode;
import lombok.Getter;

@Getter
public class CustomJwtExceptionHandlerV1 extends RuntimeException{
    ErrorCode errorCode;

    public CustomJwtExceptionHandlerV1(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public CustomJwtExceptionHandlerV1(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
