package com.david.crudjwt.services;

import com.david.crudjwt.exceptions.ItemCannotBeSavedException;
import com.david.crudjwt.exceptions.ItemsNotFoundException;
import com.david.crudjwt.models.Product;
import com.david.crudjwt.repositories.ProductRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Klasa serwisu dostępu do produktów
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Service
@Data
public class ProductService
{
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }


    /**
     * Metoda pobierająca wszystkie wpisy produktów z bazy danych
     * @return listę produktów jeśli liczba wyników jest większa od 0
     * @throws ItemsNotFoundException jeśli nie znaleziono żadnych wyników
     */
    public List<Product> getAllProducts() throws ItemsNotFoundException
    {
        List<Product> productList = getProductRepository().findAll();
        if(productList.size()>0)
        {
            return productList;
        }
        else
        {
            logger.error("No records found on {}",this.getClass().getName());
            throw new ItemsNotFoundException("No records found.",this.getClass());
        }

    }

    /**
     * Metoda pobierająca dane o produktach w sposób spaginowany
     * @param pagination dane o paginacji
     * @return gotową stronę z wynikami
     */
    public Page<Product> getALlProductsPageable(Pageable pagination)
    {
        return  getProductRepository().findAll(pagination);
    }

    /**
     * Metoda pobierająca optionala produktu po przekazanym id
     * @param id parametr po którym szukamy
     * @return optionala produktu
     */
    public Optional<Product> getProduct(Long id)
    {
        return getProductRepository().findById(id);
    }

    /**
     * Metoda do zapisu oraz aktualizacji produktu.
     * @param tempProduct encja produktu która zostaje przesłana z zapytaniem
     * @return zapisaną encję z przyznanym id
     * @throws ItemCannotBeSavedException kiedy wystąpi problem podczas zapisu
     */
    public Product saveProduct(Product tempProduct) throws ItemCannotBeSavedException
    {
        if(tempProduct!=null)
        {
            Product savedProduct = getProductRepository().save(tempProduct);
            if(savedProduct.getId()!=null)
            {
                return savedProduct;
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
