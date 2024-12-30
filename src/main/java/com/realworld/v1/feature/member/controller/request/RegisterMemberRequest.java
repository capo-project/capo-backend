package com.realworld.feature.member.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterMemberRequest {
    @JsonProperty("user_seq")
    private Long userSeq;

    @JsonProperty("user_id")
    @Pattern(regexp = "^[a-z0-9]{6,12}$",
             message = "아이디 형식이 잘못되었습니다.")
    private String userId;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
             message = "올바르지 않는 비밀번호입니다.")
    private String password;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("user_email")
    private String userEmail;
}