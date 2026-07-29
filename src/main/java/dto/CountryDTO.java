package dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDTO {
    @JsonProperty("currencies")
    public List<CurrencyDTO> currencies;

    @JsonProperty("names")
    public NameDTO name;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NameDTO {
        public String official;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrencyDTO{
            public String name;
    }
}
