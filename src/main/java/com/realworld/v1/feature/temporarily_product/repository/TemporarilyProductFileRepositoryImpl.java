package com.realworld.feature.temporarily_product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.feature.temporarily_product.domain.TemporarilyProductFile;
import com.realworld.feature.temporarily_product.entity.QTemporarilyProductFileJpaEntity;
import com.realworld.feature.temporarily_product.entity.TemporarilyProductFileJpaEntity;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class TemporarilyProductFileRepositoryImpl implements TemporarilyProductFileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QTemporarilyProductFileJpaEntity temporarilyProductFile = QTemporarilyProductFileJpaEntity.temporarilyProductFileJpaEntity;

    @Override
    public TemporarilyProductFile getFileDetails(UUID id) {
        TemporarilyProductFileJpaEntity productFileJpaEntity = queryFactory.select(temporarilyProductFile)
                .from(temporarilyProductFile)
                .where(temporarilyProductFile.id.eq(id)).fetchOne();
        return productFileJpaEntity.searchToDomain();
    }
}
