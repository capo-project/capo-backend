package com.realworld.web.member.payload.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.feature.member.entity.MemberProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "회원 정보 응답 Response")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MemberFindResponse (
        String userId,
        MemberProfile memberProfile
){


}
