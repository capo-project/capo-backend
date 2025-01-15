package com.realworld.application.member.service.member;

import com.realworld.web.member.payload.request.SignUpRequest;

public interface MemberService {

    void signUp(SignUpRequest request);

    void validateUserIdDuplicate(String userId);

}
