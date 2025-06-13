package com.expense.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String extractEmail(String token) { return getClaims(token).getSubject(); }

    public boolean isTokenExpired(String token) { return getClaims(token).getExpiration().before(new Date()); }

    public boolean validateToken(String token) {
         try {
            getClaims(token);
            return true;
         } catch (JwtException | IllegalArgumentException e) {
            return false;
         }
    }

    private Claims getClaims(String token) { return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody(); }
}
