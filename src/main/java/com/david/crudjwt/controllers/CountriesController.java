package com.david.crudjwt.controllers;

import com.david.crudjwt.exceptions.ItemsNotFoundException;
import com.david.crudjwt.services.CountryService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Klasa kontrolera REST dla endpointów krajów
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@RestController
@Data
@RequestMapping("/countries")
public class CountriesController
{
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private CountryService countryService;

    public CountriesController(CountryService countryService)
    {
        this.countryService = countryService;
    }

    /**
     * Metoda pobierająca wszystkie kraje z bazy danych
     * @return zwraca listę krajów jako ResponseEntity
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllCountries() throws ItemsNotFoundException
    {
        return ResponseEntity.ok().body(getCountryService().getAllCountries());
    }

}
