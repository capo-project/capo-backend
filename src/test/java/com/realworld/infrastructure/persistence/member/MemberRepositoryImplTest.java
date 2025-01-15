package com.realworld.infrastructure.persistence.member;

import com.realworld.application.member.port.MemberRepository;
import com.realworld.common.querydsl.TestQueryDslConfig;
import com.realworld.feature.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.realworld.feature.auth.mail.mock.member.MemberMockData.MOCK_MEMBER_DATA_ONE;
import static com.realworld.feature.auth.mail.mock.member.MemberMockData.MOCK_MEMBER_DATA_TWO;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SqlGroup({
        @Sql(value = "/sql/member/member-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/member/delete-member-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@ContextConfiguration(classes = TestQueryDslConfig.class)
@ActiveProfiles("test")
@Import(MemberRepositoryImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원_아이디_조회_성공_테스트() {
        assertThat(memberRepository.findByUserId("test1").orElse(null)).isEqualTo(MOCK_MEMBER_DATA_ONE);
    }

    @Test
    void 회원_이메일_조회_성공_테스트() {

        assertThat(memberRepository.findByUserEmail("test1234@naver.com").orElse(null)).isEqualTo(MOCK_MEMBER_DATA_ONE);
    }

    @Test
    void 회원_저장_성공_테스트() {
       memberRepository.save(MOCK_MEMBER_DATA_TWO);

        Member member = memberRepository.findByUserId("test2").orElse(null);
        assertThat(member).isEqualTo(MOCK_MEMBER_DATA_TWO);
    }

}