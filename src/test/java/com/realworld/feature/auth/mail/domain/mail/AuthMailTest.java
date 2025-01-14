package com.realworld.feature.auth.mail.domain.mail;

import com.realworld.common.exception.CustomAuthMailExceptionHandler;
import com.realworld.feature.auth.mail.entity.AuthMail;
import com.realworld.feature.auth.mail.mock.mail.MockMailData;
import com.realworld.v1.global.config.exception.CustomAuthMailExceptionHandlerV1;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class AuthMailTest {

    @Test
    void 메일_인증번호_생성_성공_테스트() {
        AuthMail authMail = AuthMail.createMail(MockMailData.userEmailMockData1, () -> "testMail", () -> LocalDateTime.of(2025, 1, 2, 12, 8, 0));
        assertThat(authMail).isEqualTo(
                AuthMail.builder()
                        .userEmail(MockMailData.userEmailMockData1)
                        .authNumber("testMail")
                        .registerDate(LocalDateTime.of(2025, 1, 2, 12, 8, 0))
                        .build()
        );
    }

    @Test
    void 메일_인증번호_체크_성공_테스트() {
        AuthMail authMail = AuthMail.createMail(MockMailData.userEmailMockData1, () -> "otirj109",  () -> LocalDateTime.of(2025, 1, 2, 12, 8, 0));

        assertThatCode(() -> authMail.authCheck("otirj109"))
                .doesNotThrowAnyException();

    }

    @Test
    void 메일_인증번호_체크_실패_코드() {
        AuthMail authMail = AuthMail.createMail(MockMailData.userEmailMockData1, () -> "otirj109", () -> LocalDateTime.of(2025, 1, 2, 12, 8, 0));

        assertThatThrownBy(() -> authMail.authCheck("otirj109dd"))
                .isInstanceOf(CustomAuthMailExceptionHandler.class);
    }

    @Test
    void 메일_인증번호_보내기_성공_테스트() {
        AuthMail authMail = AuthMail.createMail(MockMailData.userEmailMockData1, () -> "testMail", () -> LocalDateTime.of(2025, 1, 2, 12, 8, 0));

        String message = authMail.send((message1, userEmail) -> {
            return;
        });

        assertThat(message).isEqualTo("<div style='margin:100px;'><h1> 인증번호 : testMail</h1></div>");

    }

}
