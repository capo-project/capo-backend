package com.realworld.v1.feature.auth.mail;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface AuthMailServiceV1 {
    void sendAuthNumber(String userEmail) throws MessagingException, UnsupportedEncodingException;

    void checkEmailCode(String userEmail, String authNumber);
}
