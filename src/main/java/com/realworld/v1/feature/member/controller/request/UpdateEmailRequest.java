package com.realworld.v1.feature.member.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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