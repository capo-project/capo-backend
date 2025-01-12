package com.realworld.application.auth.mail.service;


import com.realworld.web.auth.mail.payload.request.AuthMailRequest;

public interface AuthMailService {

    void send(final AuthMailRequest request);

    void check(final String userEmail, final String authNumber);

}
