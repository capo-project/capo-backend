package com.realworld.v1.feature.file.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileJpaEntity is a Querydsl query type for FileJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileJpaEntity extends EntityPathBase<FileJpaEntity> {

    private static final long serialVersionUID = 1554231589L;

    public static final QFileJpaEntity fileJpaEntity = new QFileJpaEntity("fileJpaEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath extension = createString("extension");

    public final BooleanPath hasThumbnail = createBoolean("hasThumbnail");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath path = createString("path");

    public final NumberPath<Long> size = createNumber("size", Long.class);

    public QFileJpaEntity(String variable) {
        super(FileJpaEntity.class, forVariable(variable));
    }

    public QFileJpaEntity(Path<? extends FileJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileJpaEntity(PathMetadata metadata) {
        super(FileJpaEntity.class, metadata);
    }

}

