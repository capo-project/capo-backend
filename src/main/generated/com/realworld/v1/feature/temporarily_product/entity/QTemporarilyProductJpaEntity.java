package com.realworld.v1.feature.temporarily_product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTemporarilyProductJpaEntity is a Querydsl query type for TemporarilyProductJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTemporarilyProductJpaEntity extends EntityPathBase<TemporarilyProductJpaEntity> {

    private static final long serialVersionUID = 907044422L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTemporarilyProductJpaEntity temporarilyProductJpaEntity = new QTemporarilyProductJpaEntity("temporarilyProductJpaEntity");

    public final EnumPath<com.realworld.v1.global.category.GroupCategory> category = createEnum("category", com.realworld.v1.global.category.GroupCategory.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final StringPath hashtag = createString("hashtag");

    public final ListPath<TemporarilyProductFileJpaEntity, QTemporarilyProductFileJpaEntity> images = this.<TemporarilyProductFileJpaEntity, QTemporarilyProductFileJpaEntity>createList("images", TemporarilyProductFileJpaEntity.class, QTemporarilyProductFileJpaEntity.class, PathInits.DIRECT2);

    public final com.realworld.v1.feature.member.entity.QMemberJpaEntity member;

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> productSeq = createNumber("productSeq", Long.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final StringPath userId = createString("userId");

    public QTemporarilyProductJpaEntity(String variable) {
        this(TemporarilyProductJpaEntity.class, forVariable(variable), INITS);
    }

    public QTemporarilyProductJpaEntity(Path<? extends TemporarilyProductJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTemporarilyProductJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTemporarilyProductJpaEntity(PathMetadata metadata, PathInits inits) {
        this(TemporarilyProductJpaEntity.class, metadata, inits);
    }

    public QTemporarilyProductJpaEntity(Class<? extends TemporarilyProductJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.realworld.v1.feature.member.entity.QMemberJpaEntity(forProperty("member"), inits.get("member")) : null;
    }

}

