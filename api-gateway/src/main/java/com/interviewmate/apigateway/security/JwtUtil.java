package com.interviewmate.apigateway.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.interviewmate.apigateway.config.JwtConfig;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    //  Store secret securely in environment variables in production
    private final JwtConfig jwtConfig;
    // = System.getenv("JWT_SECRET");

    // Token validity: 10 hours
    private static final long TOKEN_VALIDITY = 1000 * 60 * 60 * 10;

    // ---------------- Token Generation ----------------
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        if (jwtConfig.getSecret() == null || jwtConfig.getSecret().isEmpty()) {
            throw new IllegalStateException("JWT secret is not set in environment variables!");
        }
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ---------------- Token Parsing ----------------
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token expired", e);
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException | IllegalArgumentException e) {
            throw new JwtException("Invalid JWT token", e);
        }
    }

    // ---------------- Helper Methods ----------------
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public UUID extractUserId(String token) {
    return extractClaim(token, claims -> {
        Object userIdObj = claims.get("userId");
        if (userIdObj instanceof String) {
            return UUID.fromString((String) userIdObj);
        } else if (userIdObj instanceof UUID) {
            return (UUID) userIdObj;
        } else {
            throw new IllegalArgumentException("Invalid userId type in JWT: " + userIdObj.getClass());
        }
    });
}

}
