package com.realworld.v1.feature.member.repository;


import com.realworld.v1.feature.member.entity.MemberJpaEntity;

public interface MemberRepositoryCustom {

    MemberJpaEntity findByUserEmail(MemberJpaEntity memberJpaEntity);
    long updatePassword(MemberJpaEntity memberJpaEntity);
    long updateEmail(MemberJpaEntity memberJpaEntity);
    long updateNickname(MemberJpaEntity memberJpaEntity);

}
