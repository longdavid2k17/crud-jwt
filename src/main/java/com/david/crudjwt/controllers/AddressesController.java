package com.david.crudjwt.controllers;

import com.david.crudjwt.exceptions.ItemCannotBeSavedException;
import com.david.crudjwt.exceptions.ItemsNotFoundException;
import com.david.crudjwt.models.Address;
import com.david.crudjwt.services.AddressesService;
import com.david.crudjwt.utils.ToJsonString;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/** Klasa kontrolera REST dla endpointów adresu
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@RestController
@Data
@RequestMapping("/addresses")
public class AddressesController
{
    private final AddressesService addressesService;

    public AddressesController(AddressesService addressesService)
    {
        this.addressesService = addressesService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAddresses() throws ItemsNotFoundException
    {
        return ResponseEntity.ok().body(getAddressesService().getAllAddresses());
    }

    /**
     * <p>Metoda służy do pobierania pojedynczego produktu. Sprawdza dostęność produktu po id. Dostępna dla wszystkich <u>zalogowanych</u> użytkowników</p>
     * @param id oznacza id danego produktu
     * @return produkt lub informację o braku danego wpisu w bazie
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAddresses(@PathVariable Long id)
    {
        if(id==null)
        {
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Id is null"));
        }
        else
        {
            Optional<Address> optionalAddress = getAddressesService().getAddress(id);
            if(optionalAddress.isPresent())
                return ResponseEntity.ok().body(optionalAddress.get());
            else
                return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Error - no product with id "+id+"!"));
        }
    }

    /**
     * <p>Metoda służy do tworzenia pojedynczego produktu. Sprawdza poprawność produktu. Dostępna dla użytkowników z prawami <b>ADMINISTRATORA LUB MODERATORA</b></p>
     * @param address instancja produktu
     * @return ResponseEntity z zapisanym produktem lub w przypadku niepowodzenia zwraca informację o błędzie
     */
    @PostMapping("/new")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createNewAddress(@Valid @RequestBody Address address) throws ItemCannotBeSavedException
    {
        if(address==null)
        {
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Sended address is null"));
        }
        else
        {
            if(address.getLine1().isEmpty() || address.getCountry() == null)
            {
                return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Sended product is null!"));
            }
            else
            {
                Address savedAddress = getAddressesService().saveAddress(address);
                return ResponseEntity.ok().body(savedAddress);
            }
        }
    }

    /**
     * <p>Metoda służy do aktualizowania pojedynczego produktu. Sprawdza poprawność produktu. ostępna dla użytkowników z prawami <b>ADMINISTRATORA LUB MODERATORA</b></p>
     * @param address instancja produktu
     * @return ResponseEntity z zapisanym produktem lub w przypadku niepowodzenia zwraca informację o błędzie
     */
    @PutMapping("/edit")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody Address address) throws ItemCannotBeSavedException
    {
        if(address==null || address.getId()==null)
        {
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Send product is null"));
        }
        else
        {
            Address updatedAddress = getAddressesService().saveAddress(address);
            return ResponseEntity.ok().body(updatedAddress);
        }
    }
}
