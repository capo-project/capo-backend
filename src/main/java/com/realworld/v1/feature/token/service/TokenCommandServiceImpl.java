package com.realworld.feature.token.service;

import com.realworld.feature.token.domain.Token;
import com.realworld.feature.token.controller.request.ReissueRequest;
import com.realworld.global.code.ErrorCode;
import com.realworld.global.config.exception.CustomJwtExceptionHandler;
import com.realworld.global.config.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import com.realworld.feature.token.entity.TokenJpaEntity;
import com.realworld.feature.token.repository.TokenRepository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenCommandServiceImpl implements TokenCommandService {

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Token saveToken(Token token) {
        TokenJpaEntity tokenEntity = tokenRepository.save(token.toEntity());

        return tokenEntity.toDomain();
    }

    @Transactional
    @Override
    public Token reissue(ReissueRequest request) {
        if(!jwtTokenProvider.validateToken(request.getRefreshToken())){
            throw new CustomJwtExceptionHandler(ErrorCode.JWT_TOKEN_REQUEST_ERROR);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(request.getAccessToken());

        Token newToken = jwtTokenProvider.createToken(authentication);

        Optional<TokenJpaEntity> getToken = tokenRepository.findByUserId(authentication.getName());
        getToken.ifPresent(value ->{
            value.setAccessToken(newToken.getAccessToken());
            value.setRefreshToken(newToken.getRefreshToken());
        });

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        return Token.builder()
                .userId(authentication.getName())
                .accessToken(newToken.getAccessToken())
                .refreshToken(newToken.getRefreshToken())
                .build();
    }

    @Override
    public void deleteToken(String userId) {
        TokenJpaEntity entity = TokenJpaEntity.builder()
                                    .userId(userId)
                                    .build();

        tokenRepository.delete(entity);
    }
}
