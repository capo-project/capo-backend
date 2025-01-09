package com.realworld.web.auth.mail.payload.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "인증 메일 요청 Request")
public class AuthMailRequest {

    @JsonProperty("user_email")
    @Schema(description = "유저 메일", example = "test@naver.com")
    private String userEmail;

}
