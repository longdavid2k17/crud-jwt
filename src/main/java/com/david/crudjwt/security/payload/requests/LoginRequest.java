package com.david.crudjwt.security.payload.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Klasa opakowująca dane potrzebne do wykonania procedury logowania użytkownika. Posiada pola username i password
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Data
public class LoginRequest
{
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
