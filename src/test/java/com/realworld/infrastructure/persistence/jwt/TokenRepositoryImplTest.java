package com.realworld.infrastructure.persistence.jwt;

import com.realworld.feature.auth.jwt.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.realworld.feature.auth.mail.mock.member.MemberMockData.SIGN_UP_MOCK_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenRepositoryImplTest {

    @Autowired
    TokenRedisRepository tokenRedisRepository;

    @BeforeEach

    @Test
    void 리프래쉬_토큰_저장_테스트() {
        Token token = new Token(SIGN_UP_MOCK_REQUEST.userId(), "refreshTokenTest1");
        tokenRedisRepository.save(token);

        assertThat(tokenRedisRepository.findById(SIGN_UP_MOCK_REQUEST.userId())).contains(token);
    }

    @Test
    void 리프레쉬_토큰_삭제_테스트() {
        Token token = new Token(SIGN_UP_MOCK_REQUEST.userId(), "refreshTokenTest1");
        tokenRedisRepository.save(token);
        tokenRedisRepository.deleteById(SIGN_UP_MOCK_REQUEST.userId());

        assertThat(tokenRedisRepository.findById(SIGN_UP_MOCK_REQUEST.userId())).isNotPresent();
    }

}