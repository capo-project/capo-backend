package com.realworld.v1.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.v1.global.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class ErrorResponse {
    private int status;     // 에러 상태 코드
    private String resultMsg;   // 에러 메시지
    private List<FieldError> errors;    // 상세 에러 메시지
    private String reason;      // 에러 이유

    @Builder
    protected ErrorResponse(final ErrorCode code){
        this.resultMsg = code.getMessage();
        this.status = code.getStatus();
        this.errors = new ArrayList<>();
    }

    @Builder
    protected ErrorResponse(final ErrorCode code, final String reason){
        this.resultMsg = code.getMessage();
        this.status = code.getStatus();
        this.reason = reason;
    }

    @Builder
    protected ErrorResponse(final ErrorCode code, final List<FieldError> errors){
        this.resultMsg = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult){
        return new ErrorResponse(code, FieldError.of(bindingResult));
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

        public static List<FieldError> of(final String field, final String value, final String reason){
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult){
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream().map(error -> new FieldError(
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
