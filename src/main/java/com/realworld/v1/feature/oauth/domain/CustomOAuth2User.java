package com.realworld.feature.oauth.domain;

import com.realworld.feature.auth.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User extends DefaultOAuth2User {
    private final String email;
    private final Authority authority;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String email, Authority authority) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.authority = authority;
    }
}
