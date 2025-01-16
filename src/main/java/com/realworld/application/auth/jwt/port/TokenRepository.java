package com.realworld.application.auth.jwt.port;

import com.realworld.feature.auth.jwt.Token;

import java.util.Optional;

public interface TokenRepository {

    void save(Token token);

    Optional<Token> findById(String id);

    void deleteById(String id);

    Optional<Token> findByAccessToken(String token);

}
