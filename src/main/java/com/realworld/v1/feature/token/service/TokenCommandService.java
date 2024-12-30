package com.realworld.v1.feature.token.service;


import com.realworld.v1.feature.token.controller.request.ReissueRequest;
import com.realworld.v1.feature.token.domain.Token;

public interface TokenCommandService {
    Token saveToken(Token token);

    Token reissue(ReissueRequest request);

    void deleteToken(String userId);
}
