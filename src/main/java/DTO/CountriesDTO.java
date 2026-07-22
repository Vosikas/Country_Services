package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesDTO {

    @JsonProperty("currencies")
    public List<CurrencyDTO> currencies;
    @JsonProperty("names")
    public NameDTO names;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NameDTO {
        public String official;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrencyDTO{
            public String name;
    }
}
