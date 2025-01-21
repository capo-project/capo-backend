package com.realworld.feature.auth.jwt;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@EqualsAndHashCode
@RedisHash(value = "token", timeToLive = 3* 60 * 60 * 1000)
public class Token {

    @Id
    private String id;

    @Column(nullable = false)
    private String accessToken;

    @Builder
    public Token(String id, String accessToken) {
        this.id = id;
        this.accessToken = accessToken;
    }

}
