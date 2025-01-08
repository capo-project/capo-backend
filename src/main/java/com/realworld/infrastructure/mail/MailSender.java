package com.realworld.infrastructure.mail;

@FunctionalInterface
public interface MailSender {

    void send(String message, String userEmail);

}
