package com.david.crudjwt.security.payload.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * <p>Klasa opakowująca dane potrzebne do rejestracji użytkownika w systemie.</p>
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Data
public class SignUpRequest
{
    @NotBlank
    @Size(min = 3,max = 50)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6,max = 50)
    private String password;

    private Set<String> role;
}
