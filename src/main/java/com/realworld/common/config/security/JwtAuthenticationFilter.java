package com.realworld.common.config.security;

import com.realworld.application.auth.jwt.service.JwtService;
import com.realworld.application.member.service.MemberService;
import com.realworld.common.type.jwt.JwtHeaderStatus;
import com.realworld.feature.member.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.realworld.common.config.security.SecurityConfig.PERMITTED_URI;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MemberService memberService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isPermittedURI(request.getRequestURI())) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return ;
        }

        final String accessToken = jwtService.resolveTokenFromCookie(request, JwtHeaderStatus.ACCESS_PREFIX);
        if(jwtService.validateAccessToken(accessToken)) {
            setAuthenticationToContext(accessToken);
            filterChain.doFilter(request, response);
            return ;
        }

        final String refreshToken = jwtService.resolveTokenFromCookie(request, JwtHeaderStatus.REFRESH_PREFIX);
        final Member member = findMemberByRefreshToken(refreshToken);
        if(jwtService.validateRefreshToken(refreshToken, member.getUserId())) {
            String reissuedAccessToken = jwtService.generateAccessToken(response, member);
            jwtService.generateRefreshToken(response, member);

            setAuthenticationToContext(reissuedAccessToken);
            filterChain.doFilter(request, response);
            return ;
        }

        jwtService.logout(member, response);
    }

    private boolean isPermittedURI(String requestURI) {
        return Arrays.stream(PERMITTED_URI)
                .anyMatch(permitted -> {
                    String replace = permitted.replace("*", "");
                    return requestURI.contains(replace) || replace.contains(requestURI);
                });
    }

    private Member findMemberByRefreshToken(final String refreshToken) {
        String id = jwtService.getIdFromRefresh(refreshToken);
        return memberService.findById(id);
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
