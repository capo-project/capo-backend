package com.realworld.v1.feature.token.service;

import com.realworld.v1.feature.token.controller.request.ReissueRequest;
import com.realworld.v1.feature.token.domain.Token;
import com.realworld.v1.feature.token.entity.TokenJpaEntity;
import com.realworld.v1.feature.token.repository.TokenRepositoryV1;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomJwtExceptionHandlerV1;
import com.realworld.v1.global.config.jwt.JwtTokenProviderV1;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenCommandServiceImpl implements TokenCommandService {

    private final TokenRepositoryV1 tokenRepositoryV1;
    private final JwtTokenProviderV1 jwtTokenProviderV1;

    @Override
    public Token saveToken(Token token) {
        TokenJpaEntity tokenEntity = tokenRepositoryV1.save(token.toEntity());

        return tokenEntity.toDomain();
    }

    @Transactional
    @Override
    public Token reissue(ReissueRequest request) {
        if(!jwtTokenProviderV1.validateToken(request.getRefreshToken())){
            throw new CustomJwtExceptionHandlerV1(ErrorCode.JWT_TOKEN_REQUEST_ERROR);
        }

        Authentication authentication = jwtTokenProviderV1.getAuthentication(request.getAccessToken());

        Token newToken = jwtTokenProviderV1.createToken(authentication);

        Optional<TokenJpaEntity> getToken = tokenRepositoryV1.findByUserId(authentication.getName());
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

        tokenRepositoryV1.delete(entity);
    }
}
