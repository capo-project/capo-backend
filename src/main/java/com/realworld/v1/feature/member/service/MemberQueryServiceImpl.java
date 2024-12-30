package com.realworld.feature.member.service;

import com.realworld.feature.member.domain.Member;
import com.realworld.feature.member.entity.MemberJpaEntity;
import com.realworld.feature.member.repository.MemberRepository;
import com.realworld.global.code.ErrorCode;
import com.realworld.global.config.exception.CustomMemberExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository repository;


    @Override
    public Optional<Member> findByUserEmail(String userEmail) {
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .userEmail(userEmail)
                .build();
        return Optional.ofNullable(repository.findByUserEmail(memberJpaEntity).toDomain());
    }

    @Override
    public Member getMemberByUserId(String userId) {
        MemberJpaEntity member = repository.findByUserId(userId);
        if (member == null) throw new CustomMemberExceptionHandler(ErrorCode.NOT_EXISTS_USERID);
        
        return member.toDomain();
    }

    @Override
    public Optional<Member> findMemberByUserId(String userId) {
        Optional<MemberJpaEntity> member = Optional.ofNullable(repository.findByUserId(userId));

        return member.map(MemberJpaEntity::toDomain);
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
