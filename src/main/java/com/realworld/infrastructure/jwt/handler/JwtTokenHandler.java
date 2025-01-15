package com.realworld.infrastructure.jwt.handler;

import com.realworld.common.type.jwt.JwtHeaderStatus;
import com.realworld.common.type.jwt.TokenStatus;
import jakarta.servlet.http.Cookie;

import java.security.Key;

public interface JwtTokenHandler {

    TokenStatus getTokenStatus(final String token, final Key secretKey);

    String resolveTokenFromCookie(Cookie[] cookies, JwtHeaderStatus status);

    Key getSigningKey(String secretKey);

    Cookie refreshToken(JwtHeaderStatus status);



}
