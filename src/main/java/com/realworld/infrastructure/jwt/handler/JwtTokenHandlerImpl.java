package com.realworld.infrastructure.jwt.handler;

import com.realworld.common.exception.custom.CustomJwtExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.common.type.jwt.TokenStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;

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
                    .parseClaimsJws(token); // JWT 파싱하여 검증
            return TokenStatus.AUTHENTICATED;
        } catch (ExpiredJwtException | IllegalArgumentException e) { // 만료 예외
            log.error(ErrorCode.JWT_TOKEN_REQUEST_ERROR.getMessage());
            return TokenStatus.EXPIRED;
        } catch(JwtException e) { // 기타 Jwt 예외
            log.error(e.getMessage());
            throw new CustomJwtExceptionHandler(ErrorCode.JWT_UNKNOWN_ERROR);
        }
    }

}
