package com.sangam.connect.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String key;

    private SecretKey getSigningKey(){
        byte[] arr=key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(arr);

    }
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().claims()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+(24*60*60*1000)))
                .and()
                .signWith(getSigningKey())
                .compact();

    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token,UserDetails userDetails){
        try{
            Claims claims=extractClaims(token);
            String userName= claims.getSubject();
            Date expiry=claims.getExpiration();
            if(userName.equals(userDetails.getUsername())&&expiry.after(new Date())) return true;
            return false;
        }catch(Exception e){
            System.err.println("JWT Validation failed: " + e.getMessage());
            return false;
        }
    }


}
