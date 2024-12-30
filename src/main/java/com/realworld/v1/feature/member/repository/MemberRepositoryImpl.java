package com.realworld.feature.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.feature.member.entity.MemberJpaEntity;
import com.realworld.feature.member.entity.QMemberJpaEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MemberJpaEntity findByUserEmail(MemberJpaEntity memberJpaEntity) {
        QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;
        return jpaQueryFactory.selectFrom(member).where(member.userEmail.eq(memberJpaEntity.getUserEmail())).fetchOne();
    }

    @Override
    public long updatePassword(MemberJpaEntity memberJpaEntity) {
        QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

        return jpaQueryFactory
                .update(member)
                .set(member.password, memberJpaEntity.getPassword())
                .where(member.userId.eq(memberJpaEntity.getUserId()))
                .execute();
    }

    @Override
    public long updateEmail(MemberJpaEntity memberJpaEntity) {
        QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

        return jpaQueryFactory.update(member)
                .set(member.userEmail, memberJpaEntity.getUserEmail())
                .where(member.userId.eq(memberJpaEntity.getUserId()))
                .execute();
    }

    @Override
    public long updateNickname(MemberJpaEntity memberJpaEntity) {
        QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

        return jpaQueryFactory.update(member)
                .set(member.nickname, memberJpaEntity.getNickname())
                .where(member.userId.eq(memberJpaEntity.getUserId()))
                .execute();
    }


}

