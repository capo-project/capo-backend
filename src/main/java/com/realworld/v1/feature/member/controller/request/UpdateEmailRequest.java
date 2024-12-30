package com.realworld.feature.member.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateEmailRequest {

    @JsonProperty("user_email")
    private String userEmail;

    @JsonProperty("auth_number")
    private String authNumber;
}