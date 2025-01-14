package com.realworld.common.exception;

import com.realworld.common.response.code.ExceptionResponseCode;
import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 에러를 사용하기 위한 구현체
 */
public class CustomLoginExceptionHandler extends RuntimeException {
    @Getter
    private final ExceptionResponseCode exceptionResponseCode;

    @Builder
    public CustomLoginExceptionHandler(String message, ExceptionResponseCode exceptionResponseCode) {
        super(message);
        this.exceptionResponseCode = exceptionResponseCode;
    }

    @Builder
    public CustomLoginExceptionHandler(ExceptionResponseCode exceptionResponseCode) {
        super(exceptionResponseCode.getMessage());
        this.exceptionResponseCode = exceptionResponseCode;
    }
}
