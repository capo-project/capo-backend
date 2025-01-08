package com.realworld.infrastructure.persistence.auth.mail.repository;

import com.realworld.feature.auth.mail.entity.AuthMail;
import org.springframework.data.repository.CrudRepository;

public interface AuthMailRedisRepository extends CrudRepository<AuthMail, String> {

}
