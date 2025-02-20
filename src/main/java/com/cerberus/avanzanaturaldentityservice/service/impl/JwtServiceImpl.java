package com.cerberus.avanzanaturaldentityservice.service.impl;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtServiceImpl implements JwtService {

    public static final String SECRET = "5367566B5970337336762342342342341139792F4123F452811482B4D6251655468576D5A71347437";

    @Override
    public void validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSignKey()).build()
                .parseClaimsJws(token);
    }

    @Override
    public String generateToken(UserCredential userCredential) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",userCredential.getRole());
        claims.put("emailConfirmed",userCredential.getEmailConfirmed());
        return createToken(claims, userCredential);
    }

    private String createToken(Map<String, Object> claims, UserCredential userCredential) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userCredential.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 300))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
