package com.realworld.infrastructure.persistence.auth.mail.repository;

import com.realworld.application.auth.mail.port.AuthMailRepository;
import com.realworld.feature.auth.mail.entity.AuthMail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthMailRepositoryImpl implements AuthMailRepository {

    private final AuthMailRedisRepository repository;

    @Override
    public void save(final AuthMail authMail) {
        repository.save(authMail);
    }

    @Override
    public Optional<AuthMail> findByUserEmail(final String userEmail) {
        return repository.findById(userEmail);
    }
}
