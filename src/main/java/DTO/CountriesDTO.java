package DTO;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

import java.util.Map;

public class CountriesDTO {


    public Map<String,CurrencyDTO> currencies;
    public NameDTO name;



    public static class NameDTO {
        public String official;
    }
    public static class CurrencyDTO{
            public String name;
    }
}
