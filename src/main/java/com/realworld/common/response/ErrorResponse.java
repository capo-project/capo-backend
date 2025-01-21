package com.realworld.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.common.response.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponse {
    private HttpStatus httpStatus;
    private int resultCode;
    private List<FieldError> errors;    // 상세 에러 메시지
    private Object result;
    private String resultMsg;

    @Builder
    protected ErrorResponse(final ErrorCode code){
        this.resultMsg = code.getMessage();
        this.resultCode = code.getResultCode();
        this.httpStatus = code.getHttpStatus();
        this.errors = new ArrayList<>();
    }

    @Builder
    protected ErrorResponse(final ErrorCode code, final Object result){
        this.resultMsg = code.getMessage();
        this.resultCode = code.getResultCode();
        this.httpStatus = code.getHttpStatus();
        this.result = result;
    }

    @Builder
    protected ErrorResponse(final ErrorCode code, final List<FieldError> errors){
        this.resultMsg = code.getMessage();
        this.resultCode = code.getResultCode();
        this.httpStatus = code.getHttpStatus();
        this.errors = errors;
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult){
        return new ErrorResponse(code, ErrorResponse.FieldError.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode code){
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final String reason){
        return new ErrorResponse(code, reason);
    }

    @Getter
    public static class FieldError{
        private final String field;
        private final String value;
        private final String reason;

        public static List<ErrorResponse.FieldError> of(final String field, final String value, final String reason){
            List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new ErrorResponse.FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<ErrorResponse.FieldError> of(final BindingResult bindingResult){
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream().map(error -> new ErrorResponse.FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
        @Builder
        FieldError(String field, String value, String reason){
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }

}
