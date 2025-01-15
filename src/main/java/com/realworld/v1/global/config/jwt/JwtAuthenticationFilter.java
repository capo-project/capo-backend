package com.realworld.v1.global.config.jwt;

import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomJwtExceptionHandlerV1;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORZATION_HEADER = "Authorization";
    private final JwtTokenProviderV1 jwtTokenProviderV1;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        try{
            if(token != null && jwtTokenProviderV1.validateToken(token)){
                Authentication authentication = jwtTokenProviderV1.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException e) {
            throw new CustomJwtExceptionHandlerV1(ErrorCode.JWT_WRONG_TYPE_TOKEN_ERROR);
        } catch (ExpiredJwtException e){
            throw new CustomJwtExceptionHandlerV1(ErrorCode.JWT_TOKEN_EXPIRED_ERROR);
        } catch(UnsupportedJwtException e){
            throw new CustomJwtExceptionHandlerV1(ErrorCode.UNSUPPORTED_TOKEN_ERROR);
        }  catch (Exception e){
            throw new CustomJwtExceptionHandlerV1(ErrorCode.JWT_UNKNOWN_ERROR);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORZATION_HEADER);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
