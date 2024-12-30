package com.realworld.feature.token.service;

import com.realworld.feature.token.entity.TokenJpaEntity;
import com.realworld.feature.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenQueryServiceImpl implements TokenQueryService {

    private final TokenRepository tokenRepository;

    @Override
    public Optional<TokenJpaEntity> findByUserId(String userId){ return tokenRepository.findByUserId(userId);
    }
}
