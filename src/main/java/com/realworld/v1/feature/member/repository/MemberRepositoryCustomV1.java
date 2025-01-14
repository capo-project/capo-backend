package com.realworld.v1.feature.member.repository;


import com.realworld.v1.feature.member.entity.MemberJpaEntityV1;

public interface MemberRepositoryCustomV1 {

    MemberJpaEntityV1 findByUserEmail(MemberJpaEntityV1 memberJpaEntityV1);
    long updatePassword(MemberJpaEntityV1 memberJpaEntityV1);
    long updateUserEmail(MemberJpaEntityV1 memberJpaEntityV1);

}
