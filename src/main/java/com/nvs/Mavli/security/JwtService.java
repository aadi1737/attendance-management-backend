package com.nvs.Mavli.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // Production mein ise application.properties se lena, abhi hardcode test ke liye
    private final SecretKey key = Keys.hmacShaKeyFor(
            "this-is-a-very-long-secret-key-for-jwt-signing-min-32-bytes".getBytes());

    public String generateToken(String phone) {
        return Jwts.builder()
                .subject(phone)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 din valid
                .signWith(key)
                .compact();
    }

    public String extractPhone(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}