package com.md.PODBooking.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtHelper {

    @Value("${jwt.key}")
    private String keyJwt;

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
