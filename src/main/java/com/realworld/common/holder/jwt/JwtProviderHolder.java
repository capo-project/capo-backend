package com.realworld.common.holder.jwt;


import com.realworld.feature.member.entity.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProviderHolder {

    private static final String SUBJECT = "jwt";

    public String generateAccessToken(final Key accessSecret, final long accessExpiration, final Member member) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setSubject(SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(accessSecret, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(final Key refreshSecret, final long refreshExpiration, final Member member) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setSubject(SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(refreshSecret, SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    private Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getUserId());
        claims.put("role", member.getAuthority());
        return claims;
    }

}
