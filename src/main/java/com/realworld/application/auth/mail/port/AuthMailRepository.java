package com.realworld.application.auth.mail.port;

import com.realworld.feature.auth.mail.entity.AuthMail;

import java.util.Optional;

public interface AuthMailRepository {

    void save(final AuthMail authMail);

    Optional<AuthMail> findByUserEmail(final String userEmail);

}
