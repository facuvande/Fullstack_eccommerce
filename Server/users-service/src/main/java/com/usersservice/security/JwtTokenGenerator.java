package com.usersservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date nowDate = new Date();
        Date expirationDate = new Date(nowDate.getTime() + 300000);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, "firma")
                .compact();
        return token;
    }

}
