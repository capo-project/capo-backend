package com.realworld.feature.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realworld.feature.token.entity.TokenJpaEntity;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenJpaEntity, Long>, TokenRepositoryCustom {
        Optional<TokenJpaEntity> findByUserId(String userId);

}
