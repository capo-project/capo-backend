package com.realworld.v1.feature.token.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.v1.feature.token.entity.QTokenJpaEntity;
import com.realworld.v1.feature.token.entity.TokenJpaEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenRepositoryV1Impl implements TokenRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public long updateToken(TokenJpaEntity tokenJpaEntity) {
        QTokenJpaEntity token = QTokenJpaEntity.tokenJpaEntity;
        long update = jpaQueryFactory.update(token)
                .set(token.refreshToken, tokenJpaEntity.getAccessToken())
                .set(token.accessToken, tokenJpaEntity.getAccessToken())
                .where(token.userId.eq(tokenJpaEntity.getUserId()))
                .execute();
        return update;
    }
}
