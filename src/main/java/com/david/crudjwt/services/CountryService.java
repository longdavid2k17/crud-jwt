package com.david.crudjwt.services;

import com.david.crudjwt.exceptions.ItemsNotFoundException;
import com.david.crudjwt.models.Country;
import com.david.crudjwt.repositories.CountryRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Klasa serwisu dostępu do danych krajów
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Service
@Data
public class CountryService
{
    private final Logger logger = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository)
    {
        this.countryRepository = countryRepository;
    }

    /**
     * Metoda pobierająca wszystkie wpisy krajów z bazy danych
     * @return listę krajów jeśli ilość wyników jest większa od 0
     * @throws ItemsNotFoundException jeśli nie ma żadnych wyników
     */
    public List<Country> getAllCountries() throws ItemsNotFoundException
    {
        List<Country> countries = getCountryRepository().findAll();
        if(countries.size()>0)
        {
            return countries;
        }
        else
        {
            logger.error("No records found on {}",this.getClass().getName());
            throw new ItemsNotFoundException("No records found.",this.getClass());
        }
    }

    public Optional<Country> getCountry(Long id)
    {
        return  getCountryRepository().findById(id);
    }
}
