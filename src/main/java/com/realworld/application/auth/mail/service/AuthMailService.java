package com.realworld.application.auth.mail.service;


import com.realworld.web.auth.mail.payload.request.AuthMailRequest;

public interface AuthMailService {

    void send(AuthMailRequest request);

    void check(String userEmail, String authNumber);

}
