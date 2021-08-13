package com.david.crudjwt.services;

import com.david.crudjwt.exceptions.ItemCannotBeSavedException;
import com.david.crudjwt.exceptions.ItemsNotFoundException;
import com.david.crudjwt.models.Address;
import com.david.crudjwt.repositories.AddressRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Klasa serwisu dostępu do danych repozytorium Adresów
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Data
@Service
public class AddressesService
{
    private final Logger logger = LoggerFactory.getLogger(AddressesService.class);

    private final AddressRepository addressRepository;

    public AddressesService(AddressRepository addressRepository)
    {
        this.addressRepository = addressRepository;
    }

    /**
     * Metoda zwracająca listę encji adresów z bazy danych
     * @return jeśli lista posiada przynajmniej jeden wpis
     * @throws ItemsNotFoundException jeśli nie ma żadych rekordów
     */
    public List<Address> getAllAddresses() throws ItemsNotFoundException
    {
        List<Address> addresses = getAddressRepository().findAll();
        if(addresses.size()>0)
            return  addresses;
        else
        {
            logger.error("No records found on {}",this.getClass().getName());
            throw new ItemsNotFoundException("No records found.",this.getClass());
        }
    }

    /**
     * Metoda zwraca instację Optional Adresu po przekazanym parametrze id
     * @param id po tym jest szukany wpis w bazie
     * @return optional
     */
    public Optional<Address> getAddress(Long id)
    {
        return getAddressRepository().findById(id);
    }

    /**
     * Metoda do zapisu oraz aktualizacji adresu producenta.
     * @param tempAddress encja adresu która zostaje przesłana z zapytaniem
     * @return zapisaną encję z przyznanym id
     * @throws ItemCannotBeSavedException kiedy wystąpi problem podczas zapisu
     */
    public Address saveAddress(Address tempAddress) throws ItemCannotBeSavedException
    {
        if(tempAddress!=null)
        {
            Address savedAddress = getAddressRepository().save(tempAddress);
            if(savedAddress.getId()!=null)
            {
                return savedAddress;
            }
            else
                logger.error("Received object with null id! Threw on {}",this.getClass().getName());
                throw new ItemCannotBeSavedException("Received object with null id!",this.getClass());
        }
        else
        {
            logger.error("Cannot save this object, because it's null. Threw on {}",this.getClass().getName());
            throw new ItemCannotBeSavedException("Cannot save this object, because it's null!",this.getClass());
        }
    }
}
