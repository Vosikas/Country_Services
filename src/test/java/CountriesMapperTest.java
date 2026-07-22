import DTO.CountriesDTO;
import DTO.SoapDTO;
import db.Countries;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.oorsprong.websamples.TCountryCodeAndName;
import resource.CountriesMapper;
import resource.CountriesMapperImpl;
import service.CountryCaller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        mockDTO.names = new CountriesDTO.NameDTO();
        mockDTO.names.official = "America";
        CountriesDTO.CurrencyDTO mockCurrency = new CountriesDTO.CurrencyDTO();
        mockCurrency.name = "Dollar";

        Map<String,CountriesDTO.CurrencyDTO> mockMAP = new HashMap<>();
        mockMAP.put("Dollar",mockCurrency);
        List<CountriesDTO.CurrencyDTO> mockList = new ArrayList<>();
        mockList.add(mockCurrency);
        mockDTO.currencies = mockList;
        CountryCaller caller = new CountryCaller();
        Countries result = caller.mapDtoToEntity(mockDTO);

        assertEquals("America", result.names);
        assertEquals("Dollar", result.currency);
    }

    @Test
    void DtoToEntity_CurrenciesNull(){
        CountriesDTO mockDTO = new CountriesDTO();
        mockDTO.names = new CountriesDTO.NameDTO();
        mockDTO.names.official = "America";
        CountriesDTO.CurrencyDTO mockCurrency = new CountriesDTO.CurrencyDTO();
        mockCurrency = null;

       Countries result = countryCaller.mapDtoToEntity(mockDTO);
       assertEquals("America" , result.names);
       assertEquals("none", result.currency ,"Should be 'none'");
    }

    @Test
    void DtoToEntity_Currencies_MAP_EMPTY(){
        CountriesDTO mockDTO = new CountriesDTO();
        mockDTO.names = new CountriesDTO.NameDTO();
        mockDTO.names.official = "America";
        Countries result = countryCaller.mapDtoToEntity(mockDTO);
        assertEquals("America",result.names);
        assertEquals("none" , result.currency,"Should be 'none'"); }

    CountriesMapper mapper = new CountriesMapperImpl();
    @Test
    void DtoToEntinty_SOAP(){

        TCountryCodeAndName soapCountry = new TCountryCodeAndName();
        soapCountry.setSISOCode("GR");
        soapCountry.setSName("Greece");
        SoapDTO result = mapper.toSoapCountryDTO(soapCountry);
        assertNotNull(result );
        assertEquals("GR",result.getIsoCode(),"Bad ISO mapping");
        assertEquals("Greece" , result.getName(),"Bad name mapping");

    }}

