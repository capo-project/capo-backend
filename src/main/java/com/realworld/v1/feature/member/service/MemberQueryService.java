package com.realworld.v1.feature.member.service;

import com.realworld.v1.feature.member.domain.Member;

import java.util.Optional;

public interface MemberQueryService {

    Optional<Member> findMemberByUserId(String userId);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserId(String userId);

    Optional<Member> findByUserEmail(String userEmail);
    
    Member getMemberByUserId(String userId);

}
