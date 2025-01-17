package com.realworld.common.exception.custom;

import com.realworld.common.response.code.ErrorCode;
import lombok.Getter;

@Getter
public class CustomJwtExceptionHandler extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomJwtExceptionHandler(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public CustomJwtExceptionHandler(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

}
