package com.realworld.v1.feature.temporarily_product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.v1.feature.member.entity.QMemberJpaEntityV1;
import com.realworld.v1.feature.temporarily_product.domain.TemporarilyProduct;
import com.realworld.v1.feature.temporarily_product.entity.QTemporarilyProductFileJpaEntity;
import com.realworld.v1.feature.temporarily_product.entity.QTemporarilyProductJpaEntity;
import com.realworld.v1.feature.temporarily_product.entity.TemporarilyProductJpaEntity;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomProductExceptionHandlerV1;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class TemporarilyProductRepositoryImpl implements TemporarilyProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QTemporarilyProductJpaEntity temporarilyProduct = QTemporarilyProductJpaEntity.temporarilyProductJpaEntity;
    private final QTemporarilyProductFileJpaEntity temporarilyProductFile = QTemporarilyProductFileJpaEntity.temporarilyProductFileJpaEntity;
    private final QMemberJpaEntityV1 member = QMemberJpaEntityV1.memberJpaEntityV1;

    @Override
    public TemporarilyProduct temporarilyProductDetails(Long seq) {
        TemporarilyProductJpaEntity details = queryFactory
                .select(temporarilyProduct)
                .from(temporarilyProduct)
                .innerJoin(temporarilyProduct.member, member)
                .where(temporarilyProduct.productSeq.eq(seq)).fetchOne();

        if (details == null) {
            throw new CustomProductExceptionHandlerV1(ErrorCode.NOT_EXISTS_TEMPORARILY_PRODUCT);
        }
        return details.searchToDomain();
    }

    @Override
    public List<TemporarilyProduct> temporarilyProductList(String userId) {
        List<TemporarilyProductJpaEntity> temporarilyProductList = queryFactory
                .select(temporarilyProduct)
                .from(temporarilyProduct)
                .innerJoin(temporarilyProduct.member, member)
                .where(temporarilyProduct.userId.eq(userId)).fetch();

        return temporarilyProductList.stream().map(TemporarilyProductJpaEntity::searchToDomain).toList();
    }
}
