package com.bondsbackend.ms.security;

import com.bondsbackend.ms.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "bXlwYXNzd29yZGlzdmVyeXN0cm9uZ2FuZHNlY3VyZSEhJCUjJjU2Nzg";
    @Value("${security.jwt.refresh-token.expire-length:86400000}") // 24 часа в миллисекундах
    private long refreshValidityInMilliseconds;
    @Value("${security.jwt.token.expire-length:3600000}") // 1 hour in milliseconds
    private long validityInMilliseconds;
    private byte[] secretKeyBytes;

    // Инициализация
    @PostConstruct
    protected void init() {
        secretKeyBytes = java.util.Base64.getDecoder().decode(SECRET_KEY);
    }

    // Create JWT token
    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKeyBytes).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Expired or invalid JWT token");
        }
    }

    // Создание Refresh Token
    public String createRefreshToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }


    // Get username from JWT token
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKeyBytes).parseClaimsJws(token).getBody().getSubject();
    }
}
