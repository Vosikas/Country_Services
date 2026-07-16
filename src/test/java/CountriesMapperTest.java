import DTO.CountriesDTO;
import db.Countries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import service.CountryCaller;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.wildfly.common.Assert.assertNotNull;


class CountryMapperTest {

    private CountryCaller countryCaller;

    @BeforeEach
    void setUP(){
        countryCaller = new CountryCaller();
    }

    @Test
    void DtoToEntity_AllFields(){
        CountriesDTO mockDTO = new CountriesDTO();
        mockDTO.name = new CountriesDTO.NameDTO();
        mockDTO.name.official = "America";
        CountriesDTO.CurrencyDTO mockCurrency = new CountriesDTO.CurrencyDTO();
        mockCurrency.name = "Dollar";

        Map<String,CountriesDTO.CurrencyDTO> mockMAP = new HashMap<>();
        mockMAP.put("Dollar",mockCurrency);
        mockDTO.currencies = mockMAP;

        Countries result = countryCaller.mapDtoToEntity(mockDTO);

        assertNotNull(result);
        assertEquals("America" , result.name);
        assertEquals("Dollar" , result.currency);
    }

    @Test
    void DtoToEntity_CurrenciesNull(){
        CountriesDTO mockDTO = new CountriesDTO();
        mockDTO.name = new CountriesDTO.NameDTO();
        mockDTO.name.official = "America";
        CountriesDTO.CurrencyDTO mockCurrency = new CountriesDTO.CurrencyDTO();
        mockCurrency = null;

       Countries result = countryCaller.mapDtoToEntity(mockDTO);
       assertEquals("America" , result.name);
       assertEquals("none", result.currency ,"Should be 'none'");
    }

    @Test
    void DtoToEntity_Currencies_MAP_EMPTY(){
        CountriesDTO mockDTO = new CountriesDTO();
        mockDTO.name = new CountriesDTO.NameDTO();
        mockDTO.name.official = "America";
        Countries result = countryCaller.mapDtoToEntity(mockDTO);
        assertEquals("America",result.name);
        assertEquals("none" , result.currency,"Should be 'none'");

    }

}