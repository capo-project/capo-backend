package com.realworld.common.type.jwt;

import lombok.Getter;

@Getter
public enum TokenStatus {

    AUTHENTICATED,
    EXPIRED,
    INVALID;

    TokenStatus() {

    }

}
