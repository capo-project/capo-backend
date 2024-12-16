package com.realworld.feature.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.feature.like.domain.Like;
import com.realworld.feature.like.entity.ProductLikeJpaEntity;
import com.realworld.feature.like.entity.QProductLikeJpaEntity;
import com.realworld.feature.member.entity.QMemberJpaEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LikeRepositoryCustomImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QProductLikeJpaEntity like = QProductLikeJpaEntity.productLikeJpaEntity;
    private final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

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
