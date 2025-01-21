package com.realworld.infrastructure.jwt.handler;

import com.realworld.common.type.jwt.TokenStatus;

import java.security.Key;

@FunctionalInterface
public interface JwtTokenHandler {

    TokenStatus getTokenStatus(final String token, final Key secretKey);

}
