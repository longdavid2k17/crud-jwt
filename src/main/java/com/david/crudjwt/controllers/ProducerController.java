package com.david.crudjwt.controllers;

import com.david.crudjwt.exceptions.ItemsNotFoundException;
import com.david.crudjwt.models.Producer;
import com.david.crudjwt.services.ProducerService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/** Klasa kontrolera REST dla endpointów producenta
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/producers")
@Data
public class ProducerController
{
    private final Logger logger = LoggerFactory.getLogger(ProducerController.class);
    private final ProducerService producerService;

    public ProducerController(ProducerService producerService)
    {
        this.producerService = producerService;
    }

    /**
     * Metoda pobierająca i zwracająca wszystkich producentów dostępnych w bazie danych
     * @return listę wszystkich producentów jako ResponseEntity
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducers()
    {
        try
        {
            List<Producer> producerList = getProducerService().getAllProducers();
            return ResponseEntity.ok().body(producerList);
        }
        catch (ItemsNotFoundException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
