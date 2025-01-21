package com.realworld.common.exception.custom;

import com.realworld.common.response.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomProductExceptionHandler extends RuntimeException {
    private final ErrorCode errorCode;

    @Builder
    public CustomProductExceptionHandler(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public CustomProductExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
