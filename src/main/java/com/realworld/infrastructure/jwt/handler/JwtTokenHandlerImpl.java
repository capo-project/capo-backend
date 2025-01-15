package com.realworld.infrastructure.jwt.handler;

import com.realworld.common.exception.CustomJwtExceptionHandler;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.type.jwt.JwtHeaderStatus;
import com.realworld.common.type.jwt.TokenStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtTokenHandlerImpl implements JwtTokenHandler {

    @Override
    public TokenStatus getTokenStatus(final String token, final Key secretKey) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 서명 키를 설정
                    .build()
                    .parseClaimsJwt(token); // JWT 파싱하여 검증
            return TokenStatus.AUTHENTICATED;
        } catch (ExpiredJwtException | IllegalArgumentException e) { // 만료 예외
            log.error(ExceptionResponseCode.JWT_TOKEN_REQUEST_ERROR.getMessage());
            return TokenStatus.EXPIRED;
        } catch(JwtException e) { // 기타 Jwt 예외
            throw new CustomJwtExceptionHandler(ExceptionResponseCode.JWT_UNKNOWN_ERROR);
        }
    }

    @Override
    public String resolveTokenFromCookie(final Cookie[] cookies, final JwtHeaderStatus status) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(status.getValue()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");
    }

    @Override
    public Key getSigningKey(final String secretKey) {
        String encodeKey = encodeToBase64(secretKey);
        return Keys.hmacShaKeyFor(encodeKey.getBytes(StandardCharsets.UTF_8));
    }

    private String encodeToBase64(final String secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public Cookie refreshToken(final JwtHeaderStatus status) {
        Cookie cookie = new Cookie(status.getValue(), null);
        // 쿠키 만료 시간 설정
        cookie.setMaxAge(0);
        // 쿠키 애플리케이션 모든 URL 적용
        cookie.setPath("/");

        return cookie;
    }

}
