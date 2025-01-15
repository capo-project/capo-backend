package com.realworld.v1.feature.token.service;

import com.realworld.v1.feature.token.entity.TokenJpaEntity;
import com.realworld.v1.feature.token.repository.TokenRepositoryV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenQueryServiceImpl implements TokenQueryService {

    private final TokenRepositoryV1 tokenRepositoryV1;

    @Override
    public Optional<TokenJpaEntity> findByUserId(String userId){ return tokenRepositoryV1.findByUserId(userId);
    }
}
