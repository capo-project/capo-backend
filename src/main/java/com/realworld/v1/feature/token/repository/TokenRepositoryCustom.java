package com.realworld.v1.feature.token.repository;


import com.realworld.v1.feature.token.entity.TokenJpaEntity;

public interface TokenRepositoryCustom {
    long updateToken(TokenJpaEntity tokenJpaEntity);
}
