package com.david.crudjwt.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>Klasa reprezentująca adres w bazie danych. Posiada pola id, linia adresu 1, linia adresu 2, kod pocztowy, miasto oraz
 * encję kraju</p>
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@Table(name = "addresses")
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5,max = 60)
    private String line1;

    @Size(min = 5,max = 60)
    private String line2;

    @NotNull
    @Size(min = 6,max = 10)
    private String zipCode;

    @NotNull
    @Size(min = 5,max = 60)
    private String city;

    @ManyToOne
    @JoinColumn(name="country_id", nullable=false)
    private Country country;
}
