package com.realworld.feature.auth.mail;

import com.realworld.feature.auth.mail.entity.AuthMailJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthMailRepository extends JpaRepository<AuthMailJpaEntity, Long> {
    Optional<AuthMailJpaEntity> findByUserEmail(String userEmail);
}
