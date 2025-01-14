package com.realworld.v1.feature.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.v1.feature.like.domain.Like;
import com.realworld.v1.feature.like.entity.ProductLikeJpaEntity;
import com.realworld.v1.feature.like.entity.QProductLikeJpaEntity;
import com.realworld.v1.feature.member.entity.QMemberJpaEntityV1;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LikeRepositoryCustomImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QProductLikeJpaEntity like = QProductLikeJpaEntity.productLikeJpaEntity;
    private final QMemberJpaEntityV1 member = QMemberJpaEntityV1.memberJpaEntityV1;

    public List<Like> findUserProductLikes(String userId) {
        List<ProductLikeJpaEntity> likes = queryFactory.select(like)
                .from(like)
                .innerJoin(like.member, member)
                .where(
                        like.member.userId.eq(userId)
                ).fetch();
        return likes.stream().map(ProductLikeJpaEntity::getProductToDomain).toList();
    }

}
