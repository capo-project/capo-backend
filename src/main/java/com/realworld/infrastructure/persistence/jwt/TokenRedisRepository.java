package com.realworld.infrastructure.persistence.jwt;

import com.realworld.common.annotation.jpa.ExcludeFromJpaRepositories;
import com.realworld.feature.auth.jwt.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@ExcludeFromJpaRepositories
public interface TokenRedisRepository extends CrudRepository<Token, String> {

    Optional<Token> findByAccessToken(String token);

}
