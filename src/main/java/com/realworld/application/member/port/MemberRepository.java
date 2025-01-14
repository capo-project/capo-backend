package com.realworld.application.member.port;

import com.realworld.feature.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByUserEmail(String userEmail);

    Optional<Member> findByUserId(String userId);
}
