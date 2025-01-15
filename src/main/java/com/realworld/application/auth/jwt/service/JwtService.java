package com.realworld.application.auth.jwt.service;

import com.realworld.common.type.jwt.JwtHeaderStatus;
import com.realworld.feature.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface JwtService {

    void validateUser(Member member);

    String generateAccessToken(HttpServletResponse response, Member member);

    String generateRefreshToken(HttpServletResponse response, Member member);

    boolean validateAccessToken(String token);

    boolean validateRefreshToken(String token, String id);

    String resolveTokenFromCookie(HttpServletRequest request, JwtHeaderStatus tokenPrefix);

    Authentication getAuthentication(String token);

    String getIdFromRefresh(String refreshToken);

    void logout(Member member, HttpServletResponse response);

}
