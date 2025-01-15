package com.realworld.common.exception;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.realworld.common.response.ExceptionResponse;
import com.realworld.common.response.code.ExceptionResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

import static com.realworld.common.response.code.ExceptionResponseCode.getExceptionResponseCode;

@Slf4j
@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class GlobalExceptionHandler {

    /**
     * API 호출 시, '객체' 혹은 '파라미터' 데이터 값이 유효하지 않은 경우
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException", ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(":");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        }
        final ExceptionResponse response = ExceptionResponse.of(ExceptionResponseCode.NOT_VALID_ERROR, String.valueOf(stringBuilder));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * API 호출 시 'Header' 내에 데이터 값이 유효하지 않은 경우
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ExceptionResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("MissingRequestHeaderException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionResponseCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 클라이언트에서 Body로 '객체' 데이터가 넘어오지 않았을 경우
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionResponseCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 잘못된 서버 요청일 경우 발생한 경우
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    protected ResponseEntity<ExceptionResponse> handleBadRequestException(HttpClientErrorException ex) {
        log.error("HttpClientErrorException.BadRequest", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionResponseCode.BAD_REQUEST_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Null값이 발생한 경우
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException e) {
        log.error("handleNullPointException", e);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionResponseCode.NULL_POINT_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Input / Output 내에서 발생한 경우
     *
     * @param ex IOException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ExceptionResponse> handleIOException(IOException ex) {
        log.error("handleIOException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionResponseCode.IO_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * com.fasterxml.jackson.core 내에 Exception 발생하는 경우
     *
     * @param ex JsonProcessingException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ExceptionResponse> handleJsonProcessingException(JsonProcessingException ex) {
        log.error("handleJsonProcessingException", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionResponseCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * com.google.gson 내에 Exception 발생하는 경우
     *
     * @param ex JsonParseException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<ExceptionResponse> handleJsonParseException(JsonParseException ex) {
        log.error("handleJsonParseException");
        final ExceptionResponse response = ExceptionResponse.of(ExceptionResponseCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 모든 Exception 경우 발생
     *
     * @param ex Exception
     * @return ResponseEntity<ErrorResponse>
     */
    protected ResponseEntity<ExceptionResponse> handleAllException(Exception ex) {
        log.error("Exception", ex);
        final ExceptionResponse response = ExceptionResponse.of(ExceptionResponseCode.INTERVAL_SERVER_ERROR, ex.getMessage() == null ? "empty" : ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 로그인시 에러
     *
     * @param ex
     * @return
     */

    @ExceptionHandler(CustomLoginExceptionHandler.class)
    protected ResponseEntity<ExceptionResponse> handleLoginCustomException(CustomLoginExceptionHandler ex) {
        log.error("LoginCustomException", ex);

        return buildErrorResponse(ex);
    }



    /**
     * 토큰 값 오류
     */
    @ExceptionHandler(CustomJwtExceptionHandler.class)
    protected ResponseEntity<ExceptionResponse> handleJwtCustomException(CustomJwtExceptionHandler ex) {
        log.error("Exception", ex);

        return buildErrorResponse(ex);
    }

    @ExceptionHandler(CustomAuthMailExceptionHandler.class)
    protected ResponseEntity<ExceptionResponse> handleMailCustomException(CustomAuthMailExceptionHandler ex) {
        log.error("Exception", ex);

        return buildErrorResponse(ex);
    }

    @ExceptionHandler(CustomProductExceptionHandler.class)
    protected ResponseEntity<ExceptionResponse> handleProductCustomException(CustomProductExceptionHandler ex) {
        log.error("Exception", ex);

        return buildErrorResponse(ex);
    }

    @ExceptionHandler(CustomMemberExceptionHandler.class)
    protected ResponseEntity<ExceptionResponse> handleMemberCustomException(CustomMemberExceptionHandler ex) {
        log.error("Exception", ex);
        return buildErrorResponse(ex);
    }

    @ExceptionHandler(CustomFileExceptionHandler.class)
    private ResponseEntity<ExceptionResponse> handleFileCustomException(CustomFileExceptionHandler ex) {
        log.error("File Exception", ex);
        return buildErrorResponse(ex);
    }

    @ExceptionHandler(CustomImageExceptionHandler.class)
    private ResponseEntity<ExceptionResponse> handleImageCustomException(CustomFileExceptionHandler ex) {
        log.error("File Exception", ex);
        return buildErrorResponse(ex);
    }

    private static <T extends RuntimeException> ResponseEntity<ExceptionResponse> buildErrorResponse(T ex) {
        final ExceptionResponseCode exceptionResponseCode = getExceptionResponseCode(ex.getMessage());
        final ExceptionResponse exceptionResponse = ExceptionResponse.of(exceptionResponseCode, ex.getMessage() == null ? "empty" : ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, exceptionResponseCode.getHttpStatus());
    }

}
