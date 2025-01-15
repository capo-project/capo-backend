package com.realworld.v1.global.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProviderV1 jwtTokenProviderV1;

    @Override
    public void configure(HttpSecurity http){
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtTokenProviderV1);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

}
