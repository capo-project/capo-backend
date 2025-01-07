package com.realworld.web.auth.mail.payload.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthMailRequest {

    @JsonProperty("user_email")
    private String userEmail;

}
