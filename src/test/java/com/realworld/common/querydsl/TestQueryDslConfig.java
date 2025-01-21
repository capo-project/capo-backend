package com.realworld.common.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.common.annotation.jpa.ExcludeFromJpaRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
        basePackages = "com.realworld",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = ExcludeFromJpaRepositories.class
        )
)
@Profile("test")
@TestConfiguration
public class TestQueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
