package com.realworld.application.auth.jwt.service;


import com.realworld.application.auth.jwt.port.TokenRepository;
import com.realworld.common.holder.jwt.JwtProviderHolder;
import com.realworld.common.type.jwt.TokenStatus;
import com.realworld.feature.member.entity.Member;
import com.realworld.infrastructure.jwt.handler.JwtTokenHandler;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Slf4j
@Service
@Transactional(readOnly = true)
public class JwtServiceImpl implements JwtService{
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final UserDetailServiceImpl userDetailService;
    private final TokenRepository tokenRepository;
    private final JwtProviderHolder jwtProviderHolder;
    private final Key accessSecretKey;
    private final long accessExpiration;

    public JwtServiceImpl(UserDetailServiceImpl userDetailService,
                          TokenRepository tokenRepository,
                          JwtProviderHolder jwtProviderHolder,
                          @Value("${jwt.access-secret}") String accessSecretKey,
                          @Value("${jwt.access-expiration}") long accessExpiration
                          ) {
        this.userDetailService = userDetailService;
        this.tokenRepository = tokenRepository;
        this.jwtProviderHolder = jwtProviderHolder;
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = accessExpiration;
    }

    @Override
    public String generateAccessToken(Member member) {
        // JWT AccessToken
        return jwtProviderHolder.generateAccessToken(accessSecretKey, accessExpiration, member);
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public boolean validateAccessToken(String token, JwtTokenHandler jwtTokenHandler) {
        TokenStatus tokenStatus = jwtTokenHandler.getTokenStatus(token, accessSecretKey);
        log.info(tokenStatus.toString());
        return  tokenStatus == TokenStatus.AUTHENTICATED && tokenRepository.findById(getUserId(token, accessSecretKey)).isPresent();
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
    public void logout(Member member) {
        tokenRepository.deleteById(member.getUserId());
    }

}
