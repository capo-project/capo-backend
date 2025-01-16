package com.realworld.common.holder.jwt;


import com.realworld.feature.member.entity.Member;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProviderHolder {

    public String generateAccessToken(final Key accessSecret, final long accessExpiration, final Member member) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setSubject(member.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(accessSecret)
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
