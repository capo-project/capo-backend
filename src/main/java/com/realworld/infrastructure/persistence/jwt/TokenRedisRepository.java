package com.realworld.infrastructure.persistence.jwt;

import com.realworld.common.annotation.ExcludeFromJpaRepositories;
import com.realworld.feature.auth.jwt.Token;
import org.springframework.data.repository.CrudRepository;

@ExcludeFromJpaRepositories
public interface TokenRedisRepository extends CrudRepository<Token, String> {

}
