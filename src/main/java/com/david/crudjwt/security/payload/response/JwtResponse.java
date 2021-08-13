package com.david.crudjwt.security.payload.response;

import lombok.Data;

import java.util.List;

/**
 * <p>Klasa opakowująca odpowiedź serwera na poprawny token JWT. Posiada pola token, id, username, email i kolekcję ról.
 * Dane są przekazywane w AuthenticationController </p>
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Data
public class JwtResponse
{
    private String token;
    private final String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, Long id, String username ,String email, List<String> roles)
    {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}