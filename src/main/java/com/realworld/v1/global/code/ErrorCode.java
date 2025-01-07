package com.realworld.v1.global.code;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 비밀번호 변경에 실패한 경우
    FAIL_PASSWORD_CHANGE(400, "패스워드 변경에 실패하였습니다."),

    // 유저 아이디가 존재하지 않은 경우
    NOT_EXISTS_USERID(400, "존재하지 않는 아이디입니다."),

    // 비밀번호가 올바르지 않습니다.
    NOT_EQUAL_PASSWORD(400, "비밀번호가 일치하지 않습니다."),

    // 아이디
    VALIDATION_USERID_ERROR(400, "아이디 형식이 올바르지 않습니다."),

    // 존재하지 않는 이메일
    NOT_EXISTS_EMAIL(400, "존재하지 않는 이메일입니다."),

    // 이메일 인증 중복
    EMAIL_DUPLICATION_ERROR(400, "이메일이 중복되었습니다."),

    // 이메일 인증 코드 오류
    EMAIL_AUTH_NUMBER_ERROR(400, "이메일 인증코드를 잘못 입력하였습니다. 다시 시도해 주세요."),

    // 인증 시간 만료
    EMAIL_EXPIRED_ERROR(408, "이메일 인증 시간이 만료되었습니다."),

    // 잘못된 이메일 요청
    EMAIL_REQUEST_ERROR(400, "이메일 인증을 다시 시도해주세요."),

    // 사용자 잘못된 요청
    LOGIN_REQUEST_ERROR(400, "비밀번호 또는 아이디가 올바르지 않습니다."),

    // 중복되는 아이디 이메일
    LOGIN_DUPLICATION_ERROR(400, "이미 존재하는 아이디 또는 이메일 입니다."),

    // TOKEN 만료
    JWT_TOKEN_EXPIRED_ERROR(401, "토큰이 만료되었습니다."),

    // 변조된 토큰
    JWT_WRONG_TYPE_TOKEN_ERROR(401, "변조된 토큰입니다."),

    // Token 오류
    JWT_TOKEN_REQUEST_ERROR(401, "토큰이 유효하지 않습니다."),

    // 토큰 오류
    JWT_UNKNOWN_ERROR(401, "토큰이 존재하지 않습니다."),

    // 변조된 토큰
    UNSUPPORTED_TOKEN_ERROR(401, "변조된 토큰입니다."),

    // 패스워드 오류
    PASSWORD_REQUEST_ERROR(400, "패스워드 형식이 올바르지 않습니다."),

    // 포토카드 게시물 조회 오류
    NOT_EXISTS_PRODUCT(400, "포토카드가 존재하지 않습니다."),

    // 포토카드 사용자 불일치 오류
    NOT_MATCHES_USER_PRODUCT(400, "글을 작성한 유저가 아닙니다."),

    // 임시 저장 포토카드 조회 오류
    NOT_EXISTS_TEMPORARILY_PRODUCT(400, "임시 저장 포토 카드가 존재하지 않습니다."),

    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(400, "Bad Request Exception"),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(400, "Required request body is missing"),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400, "Invalid Type Value"),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "Missing Servlet RequestParameter Exception"),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(400, "I/O Exception"),

    // JSON 파싱 실패
    JSON_PARSE_ERROR(400, "JsonParseException"),

    // jackson.core processing error
    JACKSON_PROCESS_ERROR(400, "com.fasterxml.jackson.core Exception"),

    // 권한이 없음
    FORBIDDEN_ERROR(403, "Forbidden Exception"),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "Not Found Exception"),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(404, "Null Point Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(404, "handle Validation Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_HEADER_ERROR(404, "Header에 데이터가 존재하지 않는 경우"),


    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERVAL_SERVER_ERROR(500, "Internal Server Error Exception"),

    // Transaction Insert Error
    INSERT_ERROR(200, "Insert Transaction Error Exception"),

    // Transaction Update Error
    UPDATE_ERROR(200, "Update Transaction Error Exception"),

    // Transaction Delete Error
    DELETE_ERROR(200, "Delete Transaction Error Exception");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
