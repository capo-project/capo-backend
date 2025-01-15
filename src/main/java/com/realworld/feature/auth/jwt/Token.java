package com.realworld.feature.auth.jwt;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@RedisHash(value = "token", timeToLive = 14 * 24 * 60 * 60 * 1000)
public class Token {

    @Id
    private String id;

    @Column(nullable = false)
    private String refreshToken;

    @Builder
    public Token(String id, String refreshToken) {
        this.id = id;
        this.refreshToken = refreshToken;
    }

}
