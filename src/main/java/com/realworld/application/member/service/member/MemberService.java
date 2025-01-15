package com.realworld.application.member.service.member;

import com.realworld.feature.member.entity.Member;
import com.realworld.web.member.payload.request.SignUpRequest;

public interface MemberService {

    Member signUp(SignUpRequest request);

    void validateUserIdDuplicate(String userId);

    Member findById(String id);

}
