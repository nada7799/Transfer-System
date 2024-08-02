package com.TransferApp.MoneyTransfer.service.sercurity;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Slf4j
@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration.ms}")
    private int jwtExpirationMs;

    public String generateToken(Authentication authentication){
        if(authentication == null){
            throw new IllegalArgumentException("authentication cannot be null");
        }
        if(!(authentication.getPrincipal() instanceof CustomerDetailsImpl)){
            throw new IllegalArgumentException("authentication principal must be of type CustomerDetailsImpl");
        }
        if(!authentication.isAuthenticated()){
            throw new IllegalArgumentException("authentication must be authenticated");
        }
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(customerDetails.getUsername())
                .claim("id", customerDetails.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)

                .compact();
    }
    public String getEmailFromJwtToken(String jwt){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwt).getBody().getSubject();
    }

    public Boolean validateJwtToken(String jwt){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwt);
            Claims claims = Jwts.parser()
                    .setSigningKey(key())
                    .parseClaimsJws(jwt)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                throw new ExpiredJwtException(Jwts.header(), claims, "The session expired");
            }
            return true;
        }
        catch (MalformedJwtException e){
           log.error("Invalid jwt {}", e.getMessage());
        }
        catch (ExpiredJwtException e){
            log.error("expired jwt {}", e.getMessage());
        }
        catch (UnsupportedJwtException e){
            log.error("unsupported jwt {}", e.getMessage());
        }
        catch (IllegalArgumentException e){
            log.error("jwt Claim string is empty {}", e.getMessage());
        }
        catch (SignatureException e){
            log.error("jwt signature does not match locally computed signature {}", e.getMessage());
        }
        return false;
    }
    private Key key(){return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));}
}
