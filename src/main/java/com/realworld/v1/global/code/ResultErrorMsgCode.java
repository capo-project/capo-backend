package com.realworld.v1.global.code;

import lombok.Getter;

@Getter
public enum ResultErrorMsgCode {
    LOGIN_VALIDATION_ERROR("비밀번호 또는 아이디가 올바르지 않습니다."),
    LOGIN_DUPLICATION_ERROR("이미 있는 회원 또는 이메일입니다.")
    ;
    private String msg;
    ResultErrorMsgCode(String msg){
        this.msg = msg;
    }
}
