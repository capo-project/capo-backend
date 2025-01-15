package com.realworld.v1.global.config.jwt;

import com.realworld.common.exception.CustomJwtExceptionHandler;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.v1.feature.token.domain.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProviderV1 {
    private static final String AUTHORITY_KEY = "auth";
    private static final String BEARER = "Bearer";

    private final Key key;

    // 30분
    private static final long ACCESS_TOKEN_VALIDATION_SECOND = 30 * 60 * 1000;

    private static final long REFRESH_TOKEN_VALIDATION_SECOND = 14 * 24 * 60 * 60 * 1000;

    public JwtTokenProviderV1(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 사용자 정보를 기반으로 토큰을 생성하여 반환 해주는 메서드
     *
     * @param authentication
     * @return TokenInfo : 토큰정보
     */
    public Token createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        long now = (new Date()).getTime();
        Date accessValidity = new Date(now + ACCESS_TOKEN_VALIDATION_SECOND);
        Date refreshValidity = new Date(now + REFRESH_TOKEN_VALIDATION_SECOND);

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITY_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(accessValidity)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITY_KEY, authorities)
                .setExpiration(refreshValidity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // TokenInfo 생성
        return Token.builder()
                .grantType(BEARER)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * JWT 토큰을 복화하여 토큰에 들어있는 정보를 꺼내는 메서드
     */
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);
        if (claims.get("auth") == null) {
            throw new CustomJwtExceptionHandler(ExceptionResponseCode.JWT_UNKNOWN_ERROR);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(
                        claims.get(AUTHORITY_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * 유효한 토큰인지 확인
     *
     * @param token
     * @return boolean 유효한 여부 반환
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        }

        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
