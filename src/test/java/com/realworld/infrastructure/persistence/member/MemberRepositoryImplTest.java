package com.realworld.infrastructure.persistence.member;

import com.realworld.application.member.port.MemberRepository;
import com.realworld.common.querydsl.TestQueryDslConfig;
import com.realworld.feature.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.realworld.feature.auth.mail.mock.member.MemberMockData.MOCK_MEMBER_DATA_ONE;

@DataJpaTest
@SqlGroup({
        @Sql(value = "/sql/member/delete-memeber-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/member/member-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
@ContextConfiguration(classes = TestQueryDslConfig.class)
@ActiveProfiles("test")
class MemberRepositoryImplTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    void 회원_조회_테스트() {
        Member member = memberRepository.findByUserId("test1").orElse(null);

        Assertions.assertThat(member).isEqualTo(MOCK_MEMBER_DATA_ONE);
    }


}