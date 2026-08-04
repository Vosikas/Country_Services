package dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.Country;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDTO {

    @JsonProperty("currencies")
    private List<CurrencyDTO> currencies;

    @JsonProperty("names")
    private Name name;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        private String official;
        public void setOfficial(String official) {
            this.official = official;
        }
        public String getOfficial(){
            return official;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrencyDTO{
            private String name;
        public void setName(String name) {
            this.name = name;
        }
        public String getName(){
            return name;
        }
    }
    public Name getName(){
        return name;
    }

    public List<CurrencyDTO> getCurrencies() {
        return currencies;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setCurrencies(List<CurrencyDTO> currencies) {
        this.currencies = currencies;
    }
}

