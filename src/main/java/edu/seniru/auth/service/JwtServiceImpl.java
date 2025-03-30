package edu.seniru.auth.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl {


    @Value("${spring.security.token.key}")
    private String SECRET_KEY;


    @Value("${spring.security.token.key.expire.time}")
    private Long expireTime;


    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims()
                .add("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expireTime))
                .and()
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }


    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        return Objects.equals(extractUsername(jwtToken),userDetails.getUsername());
    }
}
