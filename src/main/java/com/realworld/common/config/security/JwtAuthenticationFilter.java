package com.realworld.common.config.security;

import com.realworld.application.auth.jwt.service.JwtService;
import com.realworld.infrastructure.jwt.handler.JwtTokenHandlerImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtService.resolveAccessToken(request);
        if(jwtService.validateAccessToken(accessToken, new JwtTokenHandlerImpl())) {
            log.info("accessToken");
            setAuthenticationToContext(accessToken);
            filterChain.doFilter(request, response);
            return ;
        }

        log.info("unaccessToken");
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
