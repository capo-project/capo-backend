package com.realworld.v1.global.code;

public enum ResultMsgCode {
    LOGIN_SUCCESS_MSG("로그인 성공"),
    MEMBER_SUCCESS_REGIST("회원가입 성공")
    ;
    private final String msg;

    ResultMsgCode(String msg){
        this.msg = msg;
    }
}
