package com.realworld.infrastructure.persistence.jwt;

import com.realworld.application.auth.jwt.port.TokenRepository;
import com.realworld.feature.auth.jwt.Token;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenRedisRepository repository;

    @Override
    public void save(Token token) {
        repository.save(token);
    }

    @Override
    public Optional<Token> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

}
