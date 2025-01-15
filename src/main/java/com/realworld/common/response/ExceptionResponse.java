package com.realworld.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.common.response.code.ExceptionResponseCode;
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
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private int resultCode;
    private List<FieldError> errors;    // 상세 에러 메시지
    private Object result;
    private String resultMsg;

    @Builder
    protected ExceptionResponse(final ExceptionResponseCode code){
        this.resultMsg = code.getMessage();
        this.resultCode = code.getResultCode();
        this.httpStatus = code.getHttpStatus();
        this.errors = new ArrayList<>();
    }

    @Builder
    protected ExceptionResponse(final ExceptionResponseCode code, final Object result){
        this.resultMsg = code.getMessage();
        this.resultCode = code.getResultCode();
        this.httpStatus = code.getHttpStatus();
        this.result = result;
    }

    @Builder
    protected ExceptionResponse(final ExceptionResponseCode code, final List<FieldError> errors){
        this.resultMsg = code.getMessage();
        this.resultCode = code.getResultCode();
        this.httpStatus = code.getHttpStatus();
        this.errors = errors;
    }

    public static ExceptionResponse of(final ExceptionResponseCode code, final BindingResult bindingResult){
        return new ExceptionResponse(code, ExceptionResponse.FieldError.of(bindingResult));
    }

    public static ExceptionResponse of(final ExceptionResponseCode code){
        return new ExceptionResponse(code);
    }

    public static ExceptionResponse of(final ExceptionResponseCode code, final String reason){
        return new ExceptionResponse(code, reason);
    }

    @Getter
    public static class FieldError{
        private final String field;
        private final String value;
        private final String reason;

        public static List<ExceptionResponse.FieldError> of(final String field, final String value, final String reason){
            List<ExceptionResponse.FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new ExceptionResponse.FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<ExceptionResponse.FieldError> of(final BindingResult bindingResult){
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream().map(error -> new ExceptionResponse.FieldError(
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
