package com.realworld.feature.token.repository;

import com.realworld.feature.token.entity.TokenJpaEntity;

public interface TokenRepositoryCustom {
    long updateToken(TokenJpaEntity tokenJpaEntity);
}
