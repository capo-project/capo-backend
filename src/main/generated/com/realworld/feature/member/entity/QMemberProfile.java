package com.realworld.feature.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberProfile is a Querydsl query type for MemberProfile
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberProfile extends BeanPath<MemberProfile> {

    private static final long serialVersionUID = 1844869305L;

    public static final QMemberProfile memberProfile = new QMemberProfile("memberProfile");

    public final StringPath content = createString("content");

    public final StringPath nickname = createString("nickname");

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath userEmail = createString("userEmail");

    public QMemberProfile(String variable) {
        super(MemberProfile.class, forVariable(variable));
    }

    public QMemberProfile(Path<? extends MemberProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberProfile(PathMetadata metadata) {
        super(MemberProfile.class, metadata);
    }

}

