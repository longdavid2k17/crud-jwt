package com.david.crudjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Klasa reprezentująca kraj w bazie danych. Posiada id, nazwę oraz zbiór Adresów które należą do tego kraju</p>
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@Table(name = "countries")
public class Country
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy="country")
    @JsonIgnore
    private Set<Address> addressSet = new HashSet<>();

    public Country()
    {

    }

    public Country(String name)
    {
        this.name = name;
    }
}
