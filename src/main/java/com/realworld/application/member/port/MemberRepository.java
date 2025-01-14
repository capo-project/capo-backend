package com.realworld.application.member.port;

import com.realworld.feature.member.entity.Member;

import java.util.Optional;

public interface MemberRepository {

    void save(Member member);

    Optional<Member> findByUserEmail(String userEmail);

    Optional<Member> findByUserId(String userId);

}
