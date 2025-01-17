package com.realworld.feature.auth.mail.domain.member;

import com.realworld.common.exception.custom.CustomMemberExceptionHandler;
import com.realworld.feature.auth.mail.mock.member.MemberMockData;
import com.realworld.feature.member.entity.Member;
import com.realworld.feature.member.entity.MemberProfile;
import com.realworld.v1.feature.auth.Authority;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.realworld.feature.auth.mail.mock.member.MemberMockData.MOCK_MEMBER_DATA_ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    void 회원_생성_정적_팩토리_메서드_성공_테스트() {

        Member member = Member.createMember(MemberMockData.SIGN_UP_MOCK_REQUEST, () -> LocalDateTime.of(2025, 1, 14, 10, 57, 39), () -> "테스트오리");

        assertThat(member).isEqualTo(Member.builder()
                .memberProfile(MemberProfile.builder()
                        .nickname("테스트오리")
                        .userEmail("capo@gmail.com")
                        .build())
                .password("@Poiuyt09875")
                .userId("capo123")
                .authority(Authority.ROLE_USER)
                .modifyDate(LocalDateTime.of(2025, 1, 14, 10, 57, 39))
                .registerDate(LocalDateTime.of(2025, 1, 14, 10, 57, 39))
                .build());
    }

    @Test
    void 회원_생성_정적_팩터리_패스워드_불일치_실패_테스트() {
        assertThatThrownBy(() -> {
            Member.createMember(MemberMockData.SIGN_UP_MOCK_MISS_MATCH_PASSWORD_REQUEST, () -> LocalDateTime.of(2025, 1, 14, 10, 57, 39), () -> "실패오리");
        }).isInstanceOf(CustomMemberExceptionHandler.class);
    }

    @Test
    void 회원_패스워드_암호화_성공_테스트() {
        Member member = MOCK_MEMBER_DATA_ONE;

        assertThat(member.passwordEncode(password -> {
            return "@reredasdsada";
        })).isEqualTo(Member.builder()
                .userId("test1")
                .authority(Authority.ROLE_USER)
                .memberProfile(MemberProfile.builder()
                        .userEmail("test1234@naver.com")
                        .nickname("코딩잘하는 메깅")
                        .thumbnail("https://photocard.com")
                        .content("안녕하세요. 카포 테스트 입니다.")
                        .build())
                .password("@reredasdsada")
                .registerDate(LocalDateTime.of(2025,  1, 15, 10, 47, 0))
                .modifyDate(LocalDateTime.of(2025,  1, 15, 10, 47, 0))
                .build());
    }

}