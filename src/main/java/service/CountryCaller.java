package service;


import DTO.CountriesDTO;
import db.Countries;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped

public class CountryCaller {
    public List<Countries> getCountries(int page , int size){
        return Countries.findAll()
                .page(page,size)
                .list();
    }
    public Countries FindCountry(String name){
        String query_name = "%" + name + "%";
        return Countries.find("name ILIKE ?1", query_name).firstResult();
    }
    public List<Countries> FindCurrencyCode(String currency){
        return Countries.list("currency" , currency);
    }

}
