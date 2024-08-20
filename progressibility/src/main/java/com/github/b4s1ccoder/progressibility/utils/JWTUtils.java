package com.github.b4s1ccoder.progressibility.utils;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    public String generateToken(String id) {
        return Jwts.builder()
                    .claims(new HashMap<>())
                    .subject(id)
                    .header().empty().add("typ", "JWT")
                    .and()
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                    .signWith(getSecretKey())
                    .compact();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    public String extractId(String token) {
        return extractClaims(token).getSubject();
    }

    public Boolean isTokenValid(String token) {
        return !extractClaims(token).getExpiration().before(new Date());
    }
}
