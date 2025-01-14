package com.realworld.common.exception;

import com.realworld.common.response.code.ExceptionResponseCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomAuthMailExceptionHandler extends  RuntimeException{

    private final ExceptionResponseCode exceptionResponseCode;

    @Builder
    public CustomAuthMailExceptionHandler(String message, ExceptionResponseCode exceptionResponseCode){
        super(message);
        this.exceptionResponseCode = exceptionResponseCode;
    }

    @Builder
    public CustomAuthMailExceptionHandler(ExceptionResponseCode exceptionResponseCode){
        super(exceptionResponseCode.getMessage());
        this.exceptionResponseCode = exceptionResponseCode;
    }

}
