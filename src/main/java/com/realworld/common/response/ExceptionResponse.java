package com.realworld.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExceptionResponse<T> {
    private final HttpStatus httpStatus;
    private final int resultCode;
    private final T result;
    private final String resultMsg;

}
