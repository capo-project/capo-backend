package com.realworld.infrastructure.persistence.auth.mail.repository;

import com.realworld.common.annotation.ExcludeFromJpaRepositories;
import com.realworld.feature.auth.mail.entity.AuthMail;
import org.springframework.data.repository.CrudRepository;

@ExcludeFromJpaRepositories
public interface AuthMailRedisRepository extends CrudRepository<AuthMail, String> {

}
