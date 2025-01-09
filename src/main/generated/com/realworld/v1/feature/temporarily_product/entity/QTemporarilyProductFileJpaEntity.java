package com.realworld.v1.feature.temporarily_product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTemporarilyProductFileJpaEntity is a Querydsl query type for TemporarilyProductFileJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTemporarilyProductFileJpaEntity extends EntityPathBase<TemporarilyProductFileJpaEntity> {

    private static final long serialVersionUID = -579639254L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTemporarilyProductFileJpaEntity temporarilyProductFileJpaEntity = new QTemporarilyProductFileJpaEntity("temporarilyProductFileJpaEntity");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final QTemporarilyProductJpaEntity product;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath userId = createString("userId");

    public QTemporarilyProductFileJpaEntity(String variable) {
        this(TemporarilyProductFileJpaEntity.class, forVariable(variable), INITS);
    }

    public QTemporarilyProductFileJpaEntity(Path<? extends TemporarilyProductFileJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTemporarilyProductFileJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTemporarilyProductFileJpaEntity(PathMetadata metadata, PathInits inits) {
        this(TemporarilyProductFileJpaEntity.class, metadata, inits);
    }

    public QTemporarilyProductFileJpaEntity(Class<? extends TemporarilyProductFileJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QTemporarilyProductJpaEntity(forProperty("product"), inits.get("product")) : null;
    }

}

