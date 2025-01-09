package com.realworld.v1.feature.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBackUpMemberJpaEntity is a Querydsl query type for BackUpMemberJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBackUpMemberJpaEntity extends EntityPathBase<BackUpMemberJpaEntity> {

    private static final long serialVersionUID = 959869223L;

    public static final QBackUpMemberJpaEntity backUpMemberJpaEntity = new QBackUpMemberJpaEntity("backUpMemberJpaEntity");

    public final EnumPath<com.realworld.v1.feature.auth.Authority> authority = createEnum("authority", com.realworld.v1.feature.auth.Authority.class);

    public final DateTimePath<java.time.LocalDateTime> createDt = createDateTime("createDt", java.time.LocalDateTime.class);

    public final StringPath delYn = createString("delYn");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userId = createString("userId");

    public final NumberPath<Long> userSeq = createNumber("userSeq", Long.class);

    public QBackUpMemberJpaEntity(String variable) {
        super(BackUpMemberJpaEntity.class, forVariable(variable));
    }

    public QBackUpMemberJpaEntity(Path<? extends BackUpMemberJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBackUpMemberJpaEntity(PathMetadata metadata) {
        super(BackUpMemberJpaEntity.class, metadata);
    }

}

