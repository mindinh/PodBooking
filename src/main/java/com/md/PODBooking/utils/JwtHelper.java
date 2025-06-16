package com.md.PODBooking.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtHelper {

    @Value("${jwt.key}")
    private String keyJwt;

    @Value("${jwt.access-token.expiration-ms}")
    private long accessTokenExpirationMs;

    @Value("${jwt.refresh-token.expiration-ms}")
    private long refreshTokenExpirationMs;

    private String generateToken(String subject, long expirationMs) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJwt));

        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    public String generateAccessToken(String role) {
        return generateToken(role, accessTokenExpirationMs);
    }

    public String generateRefreshToken(String role) {
        return generateToken(role, refreshTokenExpirationMs);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJwt));

        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public boolean decrypt(String token) {
        boolean res = false;

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJwt));

        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            res = true;
        } catch (Exception e) {
            System.out.println("decrypt error " + e.getMessage());

        }

        return res;
    }

    public String getDataToken(String token) {
        String role = "";

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJwt));

        try {
            // lưu cái role ở đâu thì get ở đó (subject(u.getRole))
            role = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();

        } catch (Exception e) {
            System.out.println("get data token error " + e.getMessage());

        }

        return role;
    }
}
