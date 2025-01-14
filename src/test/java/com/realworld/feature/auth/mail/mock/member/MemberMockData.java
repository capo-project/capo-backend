package com.realworld.feature.auth.mail.mock.member;

import com.realworld.feature.member.entity.Member;
import com.realworld.feature.member.entity.MemberProfile;
import com.realworld.v1.feature.auth.Authority;
import com.realworld.web.member.payload.request.SignUpRequest;

import java.time.LocalDateTime;

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

    public static final Member MOCK_MEMBER_DATA_ONE = Member.builder()
            .userId("test1")
            .authority(Authority.ROLE_USER)
            .memberProfile(MemberProfile.builder()
                    .userEmail("test1234@naver.com")
                    .nickname("코딩잘하는 메깅")
                    .thumbnail("https://photocard.com")
                    .content("안녕하세요. 카포 테스트 입니다.")
                    .build())
            .password("@poiuy1234")
            .registerDate(LocalDateTime.of(2025,  1, 15, 10, 47, 0))
            .modifyDate(LocalDateTime.of(2025,  1, 15, 10, 47, 0))
            .build();

}
