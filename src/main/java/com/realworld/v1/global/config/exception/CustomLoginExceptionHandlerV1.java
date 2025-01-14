package com.realworld.v1.global.config.exception;

import com.realworld.v1.global.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 에러를 사용하기 위한 구현체
 */
public class CustomLoginExceptionHandlerV1 extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;

    @Builder
    public CustomLoginExceptionHandlerV1(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public CustomLoginExceptionHandlerV1(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
