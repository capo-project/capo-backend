package com.realworld.common.exception.custom;

import com.realworld.common.response.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomMemberExceptionHandler extends RuntimeException{
    private final ErrorCode errorCode;

    @Builder
    public CustomMemberExceptionHandler(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public CustomMemberExceptionHandler(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
