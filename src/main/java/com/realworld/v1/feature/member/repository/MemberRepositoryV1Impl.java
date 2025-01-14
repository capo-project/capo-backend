package com.realworld.v1.feature.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.v1.feature.member.entity.MemberJpaEntityV1;
import com.realworld.v1.feature.member.entity.QMemberJpaEntityV1;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryV1Impl implements MemberRepositoryCustomV1 {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MemberJpaEntityV1 findByUserEmail(MemberJpaEntityV1 memberJpaEntityV1) {
        QMemberJpaEntityV1 member = QMemberJpaEntityV1.memberJpaEntityV1;
        return jpaQueryFactory.selectFrom(member).where(member.userEmail.eq(memberJpaEntityV1.getUserEmail())).fetchOne();
    }

    @Override
    public long updatePassword(MemberJpaEntityV1 memberJpaEntityV1) {
        QMemberJpaEntityV1 member = QMemberJpaEntityV1.memberJpaEntityV1;

        return jpaQueryFactory
                .update(member)
                .set(member.password, memberJpaEntityV1.getPassword())
                .where(member.userId.eq(memberJpaEntityV1.getUserId()))
                .execute();
    }

    @Override
    public long updateUserEmail(MemberJpaEntityV1 memberJpaEntityV1) {
        QMemberJpaEntityV1 member = QMemberJpaEntityV1.memberJpaEntityV1;

        return jpaQueryFactory.update(member)
                .set(member.userEmail, memberJpaEntityV1.getUserEmail())
                .where(member.userId.eq(memberJpaEntityV1.getUserId()))
                .execute();
    }


}

