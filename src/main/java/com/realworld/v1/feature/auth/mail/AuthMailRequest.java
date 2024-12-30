package com.realworld.feature.auth.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthMailRequest {

    @JsonProperty("user_email")
    String userEmail;
}
