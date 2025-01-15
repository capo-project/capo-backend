package com.realworld.application.auth.jwt.service;


import com.realworld.application.auth.jwt.port.TokenRepository;
import com.realworld.common.exception.CustomJwtExceptionHandler;
import com.realworld.common.holder.jwt.JwtProviderHolder;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.type.jwt.JwtHeaderStatus;
import com.realworld.common.type.jwt.TokenStatus;
import com.realworld.feature.auth.jwt.Token;
import com.realworld.feature.member.entity.Member;
import com.realworld.infrastructure.jwt.handler.JwtTokenHandler;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Optional;

import static com.realworld.common.type.jwt.JwtHeaderStatus.*;

@Slf4j
@Service
@Transactional(readOnly = true)
public class JwtServiceImpl implements JwtService{

    private static final int DIVIDER = 1000;
    private final UserDetailServiceImpl userDetailService;
    private final TokenRepository tokenRepository;
    private final JwtProviderHolder jwtProviderHolder;
    private final JwtTokenHandler jwtTokenHandler;
    private final Key accessSecretKey;
    private final Key refreshSecretKey;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtServiceImpl(UserDetailServiceImpl userDetailService,
                          TokenRepository tokenRepository,
                          JwtProviderHolder jwtProviderHolder,
                          JwtTokenHandler jwtTokenHandler,
                          @Value("${jwt.access-secret}") String accessSecretKey,
                          @Value("${jwt.refresh-secret}") String refreshSecretKey,
                          @Value("${jwt.access-expiration}") long accessExpiration,
                          @Value("${jwt.refresh-expiration}") long refreshExpiration) {
        this.userDetailService = userDetailService;
        this.tokenRepository = tokenRepository;
        this.jwtProviderHolder = jwtProviderHolder;
        this.jwtTokenHandler = jwtTokenHandler;
        this.accessSecretKey = new SecretKeySpec(accessSecretKey.getBytes(), SignatureAlgorithm.HS256.getValue());
        this.refreshSecretKey = new SecretKeySpec(refreshSecretKey.getBytes(), SignatureAlgorithm.HS256.getValue());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    @Override
    public void validateUser(final Member member) {
        if(member.getAuthority() == null) {
            throw new CustomJwtExceptionHandler(ExceptionResponseCode.UNSUPPORTED_TOKEN_ERROR);
        }
    }

    @Override
    public String generateAccessToken(HttpServletResponse response, Member member) {
        // JWT AccessToken
        String accessToken = jwtProviderHolder.generateAccessToken(accessSecretKey, accessExpiration, member);

        // 쿠키 생성 (유효 기간은 초 단위로 설정)
        ResponseCookie cookie = setTokenToCookie(ACCESS_PREFIX.getValue(), accessToken, accessExpiration / DIVIDER);

        // 응답 헤더에 쿠키 추가
        response.addHeader(JWT_ISSUE_HEADER.getValue(), cookie.toString());

        // accessToken 반환
        return accessToken;
    }

    @Override
    @Transactional
    public String generateRefreshToken(HttpServletResponse response, Member member) {
        String refreshToken = jwtProviderHolder.generateRefreshToken(refreshSecretKey, refreshExpiration, member);
        ResponseCookie cookie = setTokenToCookie(REFRESH_PREFIX.getValue(), refreshToken, refreshExpiration / DIVIDER);
        response.addHeader(JWT_ISSUE_HEADER.getValue(), cookie.toString());

        tokenRepository.save(new Token(member.getUserId(), refreshToken));
        return refreshToken;
    }

    private ResponseCookie setTokenToCookie(String tokenPrefix, String token, long maxAgeSeconds) {
        return ResponseCookie.from(tokenPrefix, token)
                .path("/")
                .maxAge(maxAgeSeconds)
                .httpOnly(true) // 클라이언트 자바스크립트에서 접근하지 못하도록 설정
                .sameSite("None") // cors 요청에서도 쿠키를 사용할 수 있도록 설정
                .build();
    }

    @Override
    public boolean validateAccessToken(String token) {
        return jwtTokenHandler.getTokenStatus(token, accessSecretKey) == TokenStatus.AUTHENTICATED;
    }

    @Override
    public boolean validateRefreshToken(String token, String id) {
        boolean isRefreshValid = jwtTokenHandler.getTokenStatus(token,  refreshSecretKey) == TokenStatus.AUTHENTICATED;
        boolean isTokenMatched = false;
        Optional<Token> storeToken = tokenRepository.findById(id);

        if(storeToken.isPresent()) {
            isTokenMatched = storeToken.get().getRefreshToken().equals(token);
        }

        return isRefreshValid && isTokenMatched;
    }

    @Override
    public String resolveTokenFromCookie(HttpServletRequest request, JwtHeaderStatus tokenPrefix) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            throw new CustomJwtExceptionHandler(ExceptionResponseCode.JWT_TOKEN_NOT_FOUND);
        }
        return jwtTokenHandler.resolveTokenFromCookie(cookies, tokenPrefix);
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(getUserId(token, accessSecretKey));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserId(String token, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public String getIdFromRefresh(String refreshToken) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(refreshSecretKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new CustomJwtExceptionHandler(ExceptionResponseCode.JWT_TOKEN_REQUEST_ERROR);
        }

    }

    @Override
    public void logout(Member member, HttpServletResponse response) {
        tokenRepository.deleteById(member.getUserId());

        Cookie accessCookie = jwtTokenHandler.refreshToken(ACCESS_PREFIX);
        Cookie refreshCookie = jwtTokenHandler.refreshToken(REFRESH_PREFIX);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }

}
