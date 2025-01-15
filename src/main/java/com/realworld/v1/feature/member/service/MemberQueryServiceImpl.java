package com.realworld.v1.feature.member.service;

import com.realworld.v1.feature.member.domain.Member;
import com.realworld.v1.feature.member.entity.MemberJpaEntityV1;
import com.realworld.v1.feature.member.repository.MemberRepositoryV1;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomMemberExceptionHandlerV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepositoryV1 repository;


    @Override
    public Optional<Member> findByUserEmail(String userEmail) {
        MemberJpaEntityV1 memberJpaEntityV1 = MemberJpaEntityV1.builder()
                .userEmail(userEmail)
                .build();
        return Optional.ofNullable(repository.findByUserEmail(memberJpaEntityV1).toDomain());
    }

    @Override
    public Member getMemberByUserId(String userId) {
        MemberJpaEntityV1 member = repository.findByUserId(userId);
        if (member == null) throw new CustomMemberExceptionHandlerV1(ErrorCode.NOT_EXISTS_USERID);
        
        return member.toDomain();
    }

    @Override
    public Optional<Member> findMemberByUserId(String userId) {
        Optional<MemberJpaEntityV1> member = Optional.ofNullable(repository.findByUserId(userId));

        return member.map(MemberJpaEntityV1::toDomain);
    }

    @Override
    public boolean existsByUserEmail(String userEmail) {
        return repository.existsByUserEmail(userEmail);
    }

    @Override
    public boolean existsByUserId(String userId) {
        return repository.existsByUserId(userId);
    }

}
