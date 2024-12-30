package com.realworld.feature.loginout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("password")
    private String password;
}