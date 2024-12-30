package com.realworld.feature.token.service;

import com.realworld.feature.token.domain.Token;
import com.realworld.feature.token.controller.request.ReissueRequest;

public interface TokenCommandService {
    Token saveToken(Token token);

    Token reissue(ReissueRequest request);

    void deleteToken(String userId);
}
