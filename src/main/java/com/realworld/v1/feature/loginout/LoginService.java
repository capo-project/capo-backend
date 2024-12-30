package com.realworld.feature.loginout;

import com.realworld.feature.member.domain.Member;
import com.realworld.feature.token.domain.Token;

public interface LoginService {
    Token loginAndGetToken(Member member);

}
