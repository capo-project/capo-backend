package com.realworld.common.exception.custom;

import com.realworld.common.response.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomAuthMailExceptionHandler extends  RuntimeException{

    private final ErrorCode errorCode;

    @Builder
    public CustomAuthMailExceptionHandler(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public CustomAuthMailExceptionHandler(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
