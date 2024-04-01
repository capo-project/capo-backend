package com.realworld.infra.mail;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MailSender {

    void send(String userEmail, String code) throws MessagingException, UnsupportedEncodingException;
}
