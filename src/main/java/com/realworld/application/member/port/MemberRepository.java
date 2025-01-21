package com.realworld.application.member.port;

import com.realworld.feature.member.entity.Member;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findByUserEmail(String userEmail);

    Optional<Member> findByUserId(String userId);

    void delete(Member member);

}
