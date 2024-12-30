package com.realworld.v1.feature.token.service;


import com.realworld.v1.feature.token.entity.TokenJpaEntity;

import java.util.Optional;

public interface TokenQueryService {
    Optional<TokenJpaEntity> findByUserId(String userId);
}
