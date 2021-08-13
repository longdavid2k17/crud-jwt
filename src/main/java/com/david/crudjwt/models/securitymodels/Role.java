package com.david.crudjwt.models.securitymodels;

import lombok.Data;

import javax.persistence.*;

/**
 * Klasa roli, będąca encją w bazie danych. Posiada pole id oraz nazwa roli
 */
@Entity
@Data
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole roleName;

    public Role()
    {

    }

    public Role(ERole roleName)
    {
        this.roleName = roleName;
    }
}
