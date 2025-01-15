package com.realworld.web.member.payload.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "로그인 요청 Request")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record LoginRequest(
        @Schema(description = "유저 아이디", example = "test1234")
        @NotNull
        String userEmail,
        @Schema(description = "패스워드", example = "@poiuyy09876")
        @NotNull
        String password
) {
}
