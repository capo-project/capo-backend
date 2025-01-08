package com.realworld.infrastructure.persistence.auth.mail.repository;

import com.realworld.application.auth.mail.port.AuthMailRepository;
import com.realworld.feature.auth.mail.entity.AuthMail;
import com.realworld.feature.auth.mail.mock.mail.MockMailData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthMailRepositoryImplTest {

    @Autowired
    AuthMailRepository repository;

    @Test
    void 메일_저장_테스트() {
        AuthMail authMail = AuthMail.createMail(MockMailData.userEmailMockData1, () -> "otirj109", () -> LocalDateTime.of(2025, 1, 2, 12, 8, 0));
        repository.save(authMail);

        AuthMail expected = repository.findByUserEmail(MockMailData.userEmailMockData1).get();
        assertThat(authMail).isEqualTo(expected);
    }

}