package com.realworld.application.auth.jwt.service;

import com.realworld.application.auth.jwt.port.TokenRepository;
import com.realworld.application.member.port.MemberRepository;
import com.realworld.feature.auth.jwt.Token;
import com.realworld.feature.auth.jwt.mock.MockJwtData;
import com.realworld.feature.auth.mail.mock.member.MemberMockData;
import com.realworld.feature.member.entity.Member;
import com.realworld.infrastructure.jwt.handler.JwtTokenHandlerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtServiceImplTest {

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private Member member = MemberMockData.MOCK_MEMBER_DATA_ONE;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @AfterEach
    void after() {
        memberRepository.delete(member);
    }

    @Test
    void 토큰_생성_성공_테스트() {
        String accessToken = jwtService.generateAccessToken(member);
        tokenRepository.save(new Token(member.getUserId(),accessToken));

        assertThat(accessToken).isNotNull();
    }


    @Test
    void 토큰_액세서스_토큰_가져오기_테스트() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        mockRequest.addHeader("Authorization", "Bearer " + MockJwtData.JWT_TOKEN);

        assertThat(jwtService.resolveAccessToken(mockRequest)).isEqualTo(MockJwtData.JWT_TOKEN);
    }

    @Test
    void 토큰_검증_로직_성공_테스트() {
        String accessToken = jwtService.generateAccessToken(member);
        boolean result = jwtService.validateAccessToken(accessToken, new JwtTokenHandlerImpl());
        assertThat(result).isTrue();
    }

    @Test
    void 토큰_권한_인증_성공_테스트() {
        String accessToken = jwtService.generateAccessToken(member);
        Authentication authentication = jwtService.getAuthentication(accessToken);
        tokenRepository.save(new Token(member.getUserId(),accessToken));

        assertThat(authentication.getName()).isEqualTo("test1");
    }

}