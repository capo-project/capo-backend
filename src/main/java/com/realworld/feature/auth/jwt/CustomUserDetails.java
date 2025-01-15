package com.realworld.feature.auth.jwt;

import com.realworld.common.type.jwt.Authority;
import com.realworld.feature.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String userId;

    private final String email;

    private final List<Authority> roles;

    public CustomUserDetails(final String userId, final String email, final List<Authority> roles) {
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }

    public CustomUserDetails(final Member member) {
        this.userId = member.getUserId();
        this.email = member.getMemberProfile().getUserEmail();
        this.roles = List.of(Authority.valueOf(member.getAuthority().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(getRoles().stream().map(Authority::toString).map(SimpleGrantedAuthority::new).toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
