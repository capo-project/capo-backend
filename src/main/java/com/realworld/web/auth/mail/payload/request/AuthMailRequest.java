package com.realworld.web.auth.mail.payload.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "인증 메일 요청 Request")
public record AuthMailRequest(
        @JsonProperty("user_email") @Schema(description = "유저 메일", example = "test@naver.com")
        @NotBlank(message = "유저 이메일은 필수 입니다.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        String userEmail) {

}
