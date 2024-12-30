package com.realworld.feature.member.controller.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.ToString;

import java.util.UUID;

@ToString
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberResponse {

    private String userId;

    private String phoneNumber;

    private String userEmail;

    private String nickname;

    private UUID fileId;
}
