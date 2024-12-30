package com.realworld.v1.feature.loginout;


import com.realworld.v1.feature.member.domain.Member;
import com.realworld.v1.feature.token.domain.Token;

public interface LoginService {
    Token loginAndGetToken(Member member);

}
