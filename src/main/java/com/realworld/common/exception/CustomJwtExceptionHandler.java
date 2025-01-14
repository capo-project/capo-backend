package com.realworld.common.exception;

import com.realworld.common.response.code.ExceptionResponseCode;
import lombok.Getter;

@Getter
public class CustomJwtExceptionHandler extends RuntimeException{

    private final ExceptionResponseCode exceptionResponseCode;

    public CustomJwtExceptionHandler(String message, ExceptionResponseCode exceptionResponseCode){
        super(message);
        this.exceptionResponseCode = exceptionResponseCode;
    }

    public CustomJwtExceptionHandler(ExceptionResponseCode exceptionResponseCode){
        this.exceptionResponseCode = exceptionResponseCode;
    }

}
