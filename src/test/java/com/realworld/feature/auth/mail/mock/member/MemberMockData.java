package com.realworld.feature.auth.mail.mock.member;

import com.realworld.web.member.payload.SignUpRequest;

public class MemberMockData {
    public static final SignUpRequest SIGN_UP_MOCK_REQUEST = SignUpRequest.builder()
            .checkPassword("@Poiuyt09875")
            .password("@Poiuyt09875")
            .userEmail("capo@gmail.com")
            .userId("capo123")
            .build();

    public static final SignUpRequest SIGN_UP_MOCK_MISS_MATCH_PASSWORD_REQUEST = SignUpRequest.builder()
            .checkPassword("@Poiuyt098751234")
            .password("@Poiuyt0987512")
            .userEmail("capo1234@gmail.com")
            .userId("capo123456")
            .build();

}
