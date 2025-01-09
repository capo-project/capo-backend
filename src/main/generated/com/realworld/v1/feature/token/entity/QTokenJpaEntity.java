package com.realworld.v1.feature.token.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTokenJpaEntity is a Querydsl query type for TokenJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTokenJpaEntity extends EntityPathBase<TokenJpaEntity> {

    private static final long serialVersionUID = 190037847L;

    public static final QTokenJpaEntity tokenJpaEntity = new QTokenJpaEntity("tokenJpaEntity");

    public final StringPath accessToken = createString("accessToken");

    public final StringPath grantType = createString("grantType");

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath userId = createString("userId");

    public QTokenJpaEntity(String variable) {
        super(TokenJpaEntity.class, forVariable(variable));
    }

    public QTokenJpaEntity(Path<? extends TokenJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTokenJpaEntity(PathMetadata metadata) {
        super(TokenJpaEntity.class, metadata);
    }

}

