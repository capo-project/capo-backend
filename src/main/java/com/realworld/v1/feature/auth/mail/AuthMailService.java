package com.realworld.feature.auth.mail;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface AuthMailService {
    void sendAuthNumber(String userEmail) throws MessagingException, UnsupportedEncodingException;

    void checkEmailCode(String userEmail, String authNumber);
}
