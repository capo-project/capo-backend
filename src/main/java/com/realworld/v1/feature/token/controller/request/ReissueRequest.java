package com.realworld.feature.token.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@Builder
public class ReissueRequest {

    private String accessToken;

    private String refreshToken;
}
