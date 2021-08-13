package com.david.crudjwt.controllers;

import com.david.crudjwt.exceptions.ItemCannotBeSavedException;
import com.david.crudjwt.exceptions.ItemsNotFoundException;
import com.david.crudjwt.models.Product;
import com.david.crudjwt.services.ProductService;
import com.david.crudjwt.utils.ToJsonString;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;


/** Klasa kontrolera REST dla endpointów produktów
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/products")
@Data
public class ProductController
{
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    /**
     * <p>Metoda służy to pobierania wszystkich produktów w formie listy. Dostępna dla wszystkich użytkowników</p>
     * @return listę pobranych z bazy danych produktów w formie ResponseEntity
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() throws ItemsNotFoundException
    {
        return ResponseEntity.ok().body(getProductService().getAllProducts());
    }

    @GetMapping("/all-page")
    public Page<Product> getAllProductsPagination(Pageable page)
    {
        return getProductService().getALlProductsPageable(page);
    }

    /**
     * <p>Metoda służy do pobierania pojedynczego produktu. Sprawdza dostęność produktu po id. Dostępna dla wszystkich <u>zalogowanych</u> użytkowników</p>
     * @param id oznacza id danego produktu
     * @return produkt lub informację o braku danego wpisu w bazie
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProduct(@PathVariable Long id)
    {
        if(id==null)
        {
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Id is null"));
        }
        else
        {
            Optional<Product> optionalProduct = productService.getProduct(id);
            if(optionalProduct.isPresent())
                return ResponseEntity.ok().body(optionalProduct.get());
            else
                return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Error - no product with id "+id+"!"));
        }
    }

    /**
     * <p>Metoda służy do tworzenia pojedynczego produktu. Sprawdza poprawność produktu. Dostępna dla użytkowników z prawami <b>ADMINISTRATORA LUB MODERATORA</b></p>
     * @param product instancja produktu
     * @return ResponseEntity z zapisanym produktem lub w przypadku niepowodzenia zwraca informację o błędzie
     */
    @PostMapping("/new")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody Product product) throws ItemCannotBeSavedException
    {
        if(product==null)
        {
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Sended product is null"));
        }
        else
        {
            if(product.getName().isEmpty() || product.getPrice() == null)
            {
                return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Sended product is null!"));
            }
            else
            {
                Product savedProduct = getProductService().saveProduct(product);
                return ResponseEntity.ok().body(savedProduct);
            }
        }
    }

    /**
     * <p>Metoda służy do aktualizowania pojedynczego produktu. Sprawdza poprawność produktu. ostępna dla użytkowników z prawami <b>ADMINISTRATORA LUB MODERATORA</b></p>
     * @param product instancja produktu
     * @return ResponseEntity z zapisanym produktem lub w przypadku niepowodzenia zwraca informację o błędzie
     */
    @PutMapping("/edit")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity updateProduct(@Valid @RequestBody Product product) throws ItemCannotBeSavedException
    {
        if(product==null || product.getId()==null)
        {
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Send product is null"));
        }
        else
        {
            Product updatedProduct = getProductService().saveProduct(product);
            return ResponseEntity.ok().body(updatedProduct);
        }
    }
}
