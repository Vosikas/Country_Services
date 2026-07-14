package DTO;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

import java.util.Map;

public class CountriesDTO {
    public NameDTO name;

    public Map<String,CurrencyDTO> currencies;


}
