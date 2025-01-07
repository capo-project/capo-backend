package com.realworld.v1.feature.auth.mail;

import com.realworld.v1.feature.auth.mail.entity.AuthMailJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthMailRepositoryV1 extends JpaRepository<AuthMailJpaEntity, Long> {
    Optional<AuthMailJpaEntity> findByUserEmail(String userEmail);
}
