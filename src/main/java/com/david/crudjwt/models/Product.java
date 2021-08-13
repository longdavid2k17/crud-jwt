package com.david.crudjwt.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * <p>Klasa produktu, będąca encją w bazie danych. Posiada pola id, name (obowiązkowe), description,imageUrl,price (obowiązkowe), creationDate oraz modificationDate które są obsługiwane
 * przez Springa</p>
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 55)
    private String name;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String imageUrl;

    @NotNull
    private Double price;

    @CreationTimestamp
    @CreatedDate
    private LocalDateTime creationDate;

    @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime modificationDate;
}
