package com.realworld.application.auth.jwt.service;

import com.realworld.feature.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateAccessToken(Member member);

    String resolveAccessToken(HttpServletRequest request);

    boolean validateAccessToken(String token);

    Authentication getAuthentication(String token);

    void logout(Member member);

}
