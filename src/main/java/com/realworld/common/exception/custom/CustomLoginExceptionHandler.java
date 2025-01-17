package com.realworld.common.exception.custom;

import com.realworld.common.response.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 에러를 사용하기 위한 구현체
 */
public class CustomLoginExceptionHandler extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;

    @Builder
    public CustomLoginExceptionHandler(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public CustomLoginExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
