package com.realworld.v1.feature.member.repository;


import com.realworld.v1.feature.member.entity.MemberJpaEntityV1;

public interface MemberRepositoryCustom {

    MemberJpaEntityV1 findByUserEmail(MemberJpaEntityV1 memberJpaEntityV1);
    long updatePassword(MemberJpaEntityV1 memberJpaEntityV1);
    long updateEmail(MemberJpaEntityV1 memberJpaEntityV1);
    long updateNickname(MemberJpaEntityV1 memberJpaEntityV1);

}
