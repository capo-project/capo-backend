package com.realworld.common.exception;

import com.realworld.common.response.code.ExceptionResponseCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomMemberExceptionHandler extends RuntimeException{
    private final ExceptionResponseCode exceptionResponseCode;

    @Builder
    public CustomMemberExceptionHandler(String message, ExceptionResponseCode exceptionResponseCode){
        super(message);
        this.exceptionResponseCode = exceptionResponseCode;
    }

    @Builder
    public CustomMemberExceptionHandler(ExceptionResponseCode exceptionResponseCode){
        super(exceptionResponseCode.getMessage());
        this.exceptionResponseCode = exceptionResponseCode;
    }

}
