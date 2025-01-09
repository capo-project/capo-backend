package com.realworld.v1.feature.auth.mail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthMailJpaEntity is a Querydsl query type for AuthMailJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthMailJpaEntity extends EntityPathBase<AuthMailJpaEntity> {

    private static final long serialVersionUID = 1048911401L;

    public static final QAuthMailJpaEntity authMailJpaEntity = new QAuthMailJpaEntity("authMailJpaEntity");

    public final StringPath authNumber = createString("authNumber");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath userEmail = createString("userEmail");

    public QAuthMailJpaEntity(String variable) {
        super(AuthMailJpaEntity.class, forVariable(variable));
    }

    public QAuthMailJpaEntity(Path<? extends AuthMailJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthMailJpaEntity(PathMetadata metadata) {
        super(AuthMailJpaEntity.class, metadata);
    }

}

