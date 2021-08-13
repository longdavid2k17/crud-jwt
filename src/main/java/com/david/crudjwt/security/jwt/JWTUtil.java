package com.david.crudjwt.security.jwt;

import com.david.crudjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Komponent JWTUtil odpowiada za generowanie tokena dla danego użytkownika oraz dostarcza podstawowe informacje oraz operacje na potrzeby korzystania z JWT</p>
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Component
@Data
public class JWTUtil implements Serializable
{
    private static final long serialVersionUID = -2550185165626007488L;
    private final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    @Value("${jwt.expiration}")
    public int jwtTokenValidity;

    @Value("${jwt.secret}")
    private String jwtSecretString;


    /**
     * Metoda generuje token dla aktualnie wskazanej autoryzacji
     * @param authentication jest używany do pobrania instancji UserDetails
     * @return JWT w postaci String'a
     */
    public String generateJwtToken(Authentication authentication)
    {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtTokenValidity))
                .signWith(SignatureAlgorithm.HS512, jwtSecretString)
                .compact();
    }

    /**
     * Pobiera użytkownika z payloadu tokena
     * @param token
     * @return nazwę użytkownika z wskazanego payloadu
     */
    public String getUsernameFromJwtToken(String token)
    {
        return Jwts.parser().setSigningKey(jwtSecretString).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Metoda sprawdzająca poprawność tokena
     * @param authToken
     * @return true jeśli token jest poprawny, false jeśli jakikolwiek atrbut jest nieprawidłowy
     */
    public boolean validateJwtToken(String authToken)
    {
        try
        {
            Jwts.parser().setSigningKey(jwtSecretString).parseClaimsJws(authToken);
            return true;
        }
        catch (SignatureException e)
        {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        }
        catch (MalformedJwtException e)
        {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        catch (ExpiredJwtException e)
        {
            logger.error("JWT token is expired: {}", e.getMessage());
        }
        catch (UnsupportedJwtException e)
        {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        }
        catch (IllegalArgumentException e)
        {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
