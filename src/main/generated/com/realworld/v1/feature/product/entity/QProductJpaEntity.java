package com.realworld.v1.feature.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductJpaEntity is a Querydsl query type for ProductJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductJpaEntity extends EntityPathBase<ProductJpaEntity> {

    private static final long serialVersionUID = 1484629655L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductJpaEntity productJpaEntity = new QProductJpaEntity("productJpaEntity");

    public final EnumPath<com.realworld.v1.global.category.GroupCategory> category = createEnum("category", com.realworld.v1.global.category.GroupCategory.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final StringPath hashtag = createString("hashtag");

    public final ListPath<ProductFileJpaEntity, QProductFileJpaEntity> images = this.<ProductFileJpaEntity, QProductFileJpaEntity>createList("images", ProductFileJpaEntity.class, QProductFileJpaEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final ListPath<com.realworld.v1.feature.like.entity.ProductLikeJpaEntity, com.realworld.v1.feature.like.entity.QProductLikeJpaEntity> likes = this.<com.realworld.v1.feature.like.entity.ProductLikeJpaEntity, com.realworld.v1.feature.like.entity.QProductLikeJpaEntity>createList("likes", com.realworld.v1.feature.like.entity.ProductLikeJpaEntity.class, com.realworld.v1.feature.like.entity.QProductLikeJpaEntity.class, PathInits.DIRECT2);

    public final com.realworld.v1.feature.member.entity.QMemberJpaEntityV1 member;

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> productSeq = createNumber("productSeq", Long.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final StringPath userId = createString("userId");

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QProductJpaEntity(String variable) {
        this(ProductJpaEntity.class, forVariable(variable), INITS);
    }

    public QProductJpaEntity(Path<? extends ProductJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductJpaEntity(PathMetadata metadata, PathInits inits) {
        this(ProductJpaEntity.class, metadata, inits);
    }

    public QProductJpaEntity(Class<? extends ProductJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.realworld.v1.feature.member.entity.QMemberJpaEntityV1(forProperty("member"), inits.get("member")) : null;
    }

}

