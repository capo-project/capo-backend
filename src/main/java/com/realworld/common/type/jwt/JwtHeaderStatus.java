package com.realworld.common.type.jwt;

import lombok.Getter;

@Getter
public enum JwtHeaderStatus {

    JWT_ISSUE_HEADER("Set-Cookie"),
    JWT_RESOLVE_HEADER("Cookie"),
    ACCESS_PREFIX("access"),
    REFRESH_PREFIX("refresh");


    private final String value;

    JwtHeaderStatus(String value) {
        this.value = value;
    }
}
