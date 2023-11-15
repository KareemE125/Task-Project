package com.ke.task.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Component
public class JwtUtils {

    // Get the jwt secret Key from the application.properties file
    @Value("${jwt.secretkey}")
    private String appSecretKey;

    // Initialize the secret key to be static using post constructor trigger
    private static String secretKey;

    @PostConstruct
    public void init() {
        secretKey = appSecretKey;
    }

    public static String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String extractUserEmail(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}