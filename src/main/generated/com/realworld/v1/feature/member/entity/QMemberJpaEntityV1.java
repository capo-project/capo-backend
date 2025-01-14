package com.realworld.v1.feature.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberJpaEntityV1 is a Querydsl query type for MemberJpaEntityV1
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberJpaEntityV1 extends EntityPathBase<MemberJpaEntityV1> {

    private static final long serialVersionUID = 1665329028L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberJpaEntityV1 memberJpaEntityV1 = new QMemberJpaEntityV1("memberJpaEntityV1");

    public final EnumPath<com.realworld.v1.feature.auth.Authority> authority = createEnum("authority", com.realworld.v1.feature.auth.Authority.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDt = createDateTime("createDt", java.time.LocalDateTime.class);

    public final StringPath delYn = createString("delYn");

    public final com.realworld.v1.feature.file.entity.QFileJpaEntity file;

    public final ListPath<com.realworld.v1.feature.like.entity.ProductLikeJpaEntity, com.realworld.v1.feature.like.entity.QProductLikeJpaEntity> likes = this.<com.realworld.v1.feature.like.entity.ProductLikeJpaEntity, com.realworld.v1.feature.like.entity.QProductLikeJpaEntity>createList("likes", com.realworld.v1.feature.like.entity.ProductLikeJpaEntity.class, com.realworld.v1.feature.like.entity.QProductLikeJpaEntity.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final StringPath oauthImage = createString("oauthImage");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userId = createString("userId");

    public QMemberJpaEntityV1(String variable) {
        this(MemberJpaEntityV1.class, forVariable(variable), INITS);
    }

    public QMemberJpaEntityV1(Path<? extends MemberJpaEntityV1> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberJpaEntityV1(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberJpaEntityV1(PathMetadata metadata, PathInits inits) {
        this(MemberJpaEntityV1.class, metadata, inits);
    }

    public QMemberJpaEntityV1(Class<? extends MemberJpaEntityV1> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.file = inits.isInitialized("file") ? new com.realworld.v1.feature.file.entity.QFileJpaEntity(forProperty("file")) : null;
    }

}

