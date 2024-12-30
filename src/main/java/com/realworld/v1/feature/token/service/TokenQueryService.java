package com.realworld.feature.token.service;

import com.realworld.feature.token.entity.TokenJpaEntity;

import java.util.Optional;

public interface TokenQueryService {
    Optional<TokenJpaEntity> findByUserId(String userId);
}
