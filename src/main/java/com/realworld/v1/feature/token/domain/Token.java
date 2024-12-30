package com.realworld.feature.token.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.feature.token.entity.TokenJpaEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Token {
    private String userId;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String userEmail;
    private String nickname;

    public TokenJpaEntity toEntity() {
        return TokenJpaEntity.builder()
                        .grantType(getGrantType())
                        .accessToken(getAccessToken())
                        .refreshToken(getRefreshToken())
                        .userId(getUserId())
                        .build();
    }
}
