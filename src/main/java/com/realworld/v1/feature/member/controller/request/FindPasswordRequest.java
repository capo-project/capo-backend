package com.realworld.feature.member.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindPasswordRequest {

    @JsonProperty("user_email")
    private String userEmail;

    @JsonProperty("new_password")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
            message = "올바르지 않는 비밀번호입니다.")
    private String newPassword;

    @JsonProperty("auth_number")
    private String authNumber;
}