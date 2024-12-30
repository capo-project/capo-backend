package com.realworld.feature.member.service;

import com.realworld.feature.member.domain.Member;

public interface MemberCommandService {
    Member saveMember(Member memberDto);

    void remove(String userId, String password);

    long updatePassword(Member member);

    long updateEmail(Member member);
}
