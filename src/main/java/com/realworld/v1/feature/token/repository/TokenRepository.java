package com.realworld.v1.feature.token.repository;


import com.realworld.v1.feature.token.entity.TokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenJpaEntity, Long>, TokenRepositoryCustom {
        Optional<TokenJpaEntity> findByUserId(String userId);

}
