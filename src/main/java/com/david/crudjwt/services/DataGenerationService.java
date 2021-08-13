package com.david.crudjwt.services;

import com.david.crudjwt.models.Address;
import com.david.crudjwt.models.Country;
import com.david.crudjwt.models.Product;
import com.david.crudjwt.models.securitymodels.ERole;
import com.david.crudjwt.models.securitymodels.Role;
import com.david.crudjwt.repositories.AddressRepository;
import com.david.crudjwt.repositories.CountryRepository;
import com.david.crudjwt.repositories.ProductRepository;
import com.david.crudjwt.repositories.RoleRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Klasa serwisu generowania danych na potrzeby testowe
 * @author Dawid Kańtoch
 * @version 1.0
 * @since 1.0
 */
@Service
@Data
public class DataGenerationService
{
    private final Logger logger = LoggerFactory.getLogger(DataGenerationService.class);

    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;

    private List<String> countriesList;
    private List<Product> productList;
    private Random generator;
    private List<String> productsNameList;

    public DataGenerationService(ProductRepository productRepository, RoleRepository roleRepository, CountryRepository countryRepository, AddressRepository addressRepository)
    {
        this.productRepository = productRepository;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
        this.addressRepository = addressRepository;

        this.productList = new ArrayList<>();
        this.generator = new Random();
        this.productsNameList = Arrays.asList("Poduszka","Kocyk","Długopis","Ołówek","Trampki","Pluszak","Gitara");
        this.countriesList = Arrays.asList("Polska","Austria","Czechy","Niemcy","Francja","Norwegia");
    }

    /**
     * Główna metoda uruchamiająca konkretne metody generowania danych po uruchomieniu aplikacji
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void fillDb()
    {
        {
            generateProducts();
            generateRoles();
            generateCountries();
            generateAddresses();
        }
        logger.info("Data generation ended!");
    }

    @Transactional
    public void generateAddresses()
    {
        Address address = null;
        for(int i=1;i<26;i++)
        {
            address = new Address();
            address.setCity("Katowice");
            address.setLine1("ul. Poniatowskiego "+i+"/10");
            address.setZipCode("42-500");
            address.setCountry(getCountryRepository().getById(1L));
            getAddressRepository().save(address);
        }

        logger.info("Saved {} records to table addresses",getAddressRepository().findAll().size());
    }

    @Transactional
    public void generateCountries()
    {
        for(String s:countriesList)
        {
            getCountryRepository().save(new Country(s));
        }
        logger.info("Saved {} records to table countries",getCountryRepository().findAll().size());
    }

    @Transactional
    public void generateProducts()
    {
        for(int i=0;i<getGenerator().nextInt(150)+250;i++)
        {
            Product product = new Product();
            product.setName("Produkt testowy: "+getProductsNameList().get(getGenerator().nextInt(getProductsNameList().size())));
            product.setDescription("Opis dla produktu testowego"+i+": "+getGenerator().nextInt(512));
            product.setPrice(1+(i*0.75)+ getGenerator().nextDouble()*500);
            product.setImageUrl("/assets/images/products/image"+getGenerator().nextInt(1024)+"_"+getGenerator().nextInt(1024)+"_"+getGenerator().nextInt(1024)+"_"+getGenerator().nextInt(1024)+".jpg");
            getProductList().add(product);
        }
        for(Product product:productList)
        {
            getProductRepository().save(product);
        }

        logger.info("Saved {} records to table products",getProductRepository().findAll().size());
    }

    @Transactional
    public void generateRoles()
    {
        getRoleRepository().save(new Role(ERole.ROLE_USER));
        getRoleRepository().save(new Role(ERole.ROLE_ADMIN));
        getRoleRepository().save(new Role(ERole.ROLE_MODERATOR));

        if(getRoleRepository().findAll().size()==3)
        {
            logger.info("Saved all 3 records to table roles");
        }
        else
        {
            logger.info("Saved {} records to table roles",getRoleRepository().findAll().size());
        }
    }

}
