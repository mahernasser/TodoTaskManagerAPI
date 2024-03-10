package com.maher.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenHandler {

    private final String CLAIMS_SUBJECT = "sub";
    private final String CLAIMS_CREATED = "created";

    @Value("${auth.expiration}")
    private long TOKEN_VALIDITY;

    @Value("${auth.secret}")
    private String TOKEN_SECRET;


    public String getUseNameFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (JwtException ignored) {
            return null;
        }
    }


    public String tokenGenerator(UserDetails userDetails) {
        // claims => expiration => sign => compact
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_SUBJECT, userDetails.getUsername());
        claims.put(CLAIMS_CREATED, new Date());
        Key key = Keys.hmacShaKeyFor(TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(claims)
                .expiration(tokenExpirationGenerator())
                .signWith(key)
                .compact();

    }

    private Date tokenExpirationGenerator() {
        return new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUseNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(TOKEN_SECRET.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            claims = null;

        }

        return claims;
    }
}