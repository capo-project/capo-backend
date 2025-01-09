package com.realworld.v1.feature.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductFileJpaEntity is a Querydsl query type for ProductFileJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductFileJpaEntity extends EntityPathBase<ProductFileJpaEntity> {

    private static final long serialVersionUID = -1951000581L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductFileJpaEntity productFileJpaEntity = new QProductFileJpaEntity("productFileJpaEntity");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final QProductJpaEntity product;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath userId = createString("userId");

    public QProductFileJpaEntity(String variable) {
        this(ProductFileJpaEntity.class, forVariable(variable), INITS);
    }

    public QProductFileJpaEntity(Path<? extends ProductFileJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductFileJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductFileJpaEntity(PathMetadata metadata, PathInits inits) {
        this(ProductFileJpaEntity.class, metadata, inits);
    }

    public QProductFileJpaEntity(Class<? extends ProductFileJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProductJpaEntity(forProperty("product"), inits.get("product")) : null;
    }

}

