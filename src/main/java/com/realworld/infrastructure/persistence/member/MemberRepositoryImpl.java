package com.realworld.infrastructure.persistence.member;

import com.realworld.application.member.port.MemberRepository;
import com.realworld.feature.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository repository;

    @Override
    public Member save(Member member) {
        return repository.save(member);
    }

    @Override
    public Optional<Member> findByUserEmail(String userEmail) {
        return repository.findByUserEmail(userEmail);
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return repository.findById(userId);
    }

}
