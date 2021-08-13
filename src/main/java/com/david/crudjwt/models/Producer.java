package com.david.crudjwt.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *<p>Klasa reprezentująca producenta w bazie danych. Posiada id, nazwę imię oraz nazwisko właściciela oraz encję Adresu który należą do tego kraju</p>
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@Table(name = "producers")
public class Producer
{
    // TODO connect Producer with other classes, generate some data, try to catch them in Postman
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 60)
    private String ownerNameAndSurname;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address producerAddress;
}
