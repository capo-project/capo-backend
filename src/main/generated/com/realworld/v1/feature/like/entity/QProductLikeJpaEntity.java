package com.realworld.v1.feature.like.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductLikeJpaEntity is a Querydsl query type for ProductLikeJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductLikeJpaEntity extends EntityPathBase<ProductLikeJpaEntity> {

    private static final long serialVersionUID = -734508112L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductLikeJpaEntity productLikeJpaEntity = new QProductLikeJpaEntity("productLikeJpaEntity");

    public final NumberPath<Long> likeSeq = createNumber("likeSeq", Long.class);

    public final com.realworld.v1.feature.member.entity.QMemberJpaEntity member;

    public final com.realworld.v1.feature.product.entity.QProductJpaEntity product;

    public QProductLikeJpaEntity(String variable) {
        this(ProductLikeJpaEntity.class, forVariable(variable), INITS);
    }

    public QProductLikeJpaEntity(Path<? extends ProductLikeJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductLikeJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductLikeJpaEntity(PathMetadata metadata, PathInits inits) {
        this(ProductLikeJpaEntity.class, metadata, inits);
    }

    public QProductLikeJpaEntity(Class<? extends ProductLikeJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.realworld.v1.feature.member.entity.QMemberJpaEntity(forProperty("member"), inits.get("member")) : null;
        this.product = inits.isInitialized("product") ? new com.realworld.v1.feature.product.entity.QProductJpaEntity(forProperty("product"), inits.get("product")) : null;
    }

}

