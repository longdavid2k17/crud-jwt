package com.david.crudjwt.services;

import com.david.crudjwt.exceptions.ItemsNotFoundException;
import com.david.crudjwt.models.Producer;
import com.david.crudjwt.repositories.ProducerRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Klasa serwisu dostępu do danych repozytorium Producentów
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Service
@Data
public class ProducerService
{
    private final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository)
    {
        this.producerRepository = producerRepository;
    }

    /**
     * Metoda zwracająca listę encji producentów z bazy danych
     * @return jeśli lista posiada przynajmniej jeden wpis
     * @throws ItemsNotFoundException jeśli nie ma żadnych rekordów
     */
    public List<Producer> getAllProducers() throws ItemsNotFoundException
    {
        List<Producer> producers = getProducerRepository().findAll();
        if(producers.size()>0)
            return producers;
        else
        {
            logger.error("No records found on {}",this.getClass().getName());
            throw new ItemsNotFoundException("No records found.",this.getClass());
        }
    }

    /**
     * Metoda zwraca instację Optional Producenta po przekazanym parametrze id
     * @param id po tym jest szukany wpis w bazie
     * @return optional
     */
    public Optional<Producer> getProducer(Long id)
    {
        return getProducerRepository().findById(id);
    }
}
