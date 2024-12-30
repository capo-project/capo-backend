package com.realworld.feature.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realworld.feature.token.domain.Token;
import com.realworld.feature.token.service.TokenCommandService;
import com.realworld.global.config.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private static final String URI = "https://photocard.site";
    private final JwtTokenProvider tokenProvider;
    private final TokenCommandService tokenCommandService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 login 성공!");
        log.info("{}", authentication.getName());
        log.info("{}", authentication);

        Token token = tokenProvider.createToken(authentication);
        token.setUserId(authentication.getName());

        tokenCommandService.saveToken(token);

        Cookie cookie = new Cookie("accessToken", token.getAccessToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                .build().toUriString();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        response.sendRedirect(redirectUrl);
    }
}
