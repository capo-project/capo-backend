package com.realworld.web.member.payload.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
@Schema(description = "회원 가입 요청 Request")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SignUpRequest(
        @Schema(description = "유저아이디", example = "capotest1")
        @NotBlank(message = "유저 아이디는 필수 값 입니다.")
        String userId,

        @Schema(description = "유저 메일", example = "test@naver.com")
        @NotBlank(message = "유저 이메일은 필수 값 입니다.")
        @Email
        String userEmail,

        @Schema(description = "패스워드 체크", example = "@poiuyy09876")
        @NotBlank(message = "패스워드 확인은 필수 값 입니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
                message = "올바르지 않는 비밀번호입니다.")
        String checkPassword,

        @Schema(description = "패스워드", example = "@poiuyy09876")
        @NotBlank(message = "패스워드는 필수 값 입니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$", message = "올바르지 않는 비밀번호입니다.")
        String password) {

}