package com.realworld.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Token {
    private Long tokenSeq;
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
