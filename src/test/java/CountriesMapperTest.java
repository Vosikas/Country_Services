import db.Country;
import dto.CountryDTO;
import dto.SoapCallResponseDTO;
import mapper.CountryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.oorsprong.websamples.TCountryCodeAndName;
import resource.CountryMapperImpl;
import service.CountryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.wildfly.common.Assert.assertNotNull;
class CountryMapperTest {
    private CountryService countryService;
    @BeforeEach
    void setUP(){
        countryService = new CountryService();
    }

    @Test
    void DtoToEntity_AllFields(){
        CountryDTO mockDTO = new CountryDTO();
        mockDTO.name = new CountryDTO.NameDTO();
        mockDTO.name.official = "America";
        CountryDTO.CurrencyDTO mockCurrency = new CountryDTO.CurrencyDTO();
        mockCurrency.name = "Dollar";

        Map<String, CountryDTO.CurrencyDTO> mockMAP = new HashMap<>();
        mockMAP.put("Dollar",mockCurrency);
        List<CountryDTO.CurrencyDTO> mockList = new ArrayList<>();
        mockList.add(mockCurrency);
        mockDTO.currencies = mockList;
        CountryService caller = new CountryService();
        Country result = caller.mapDtoToEntity(mockDTO);
        assertEquals("America", result.names);
        assertEquals("Dollar", result.currency);
    }

    @Test
    void DtoToEntity_CurrenciesNull(){
        CountryDTO mockDTO = new CountryDTO();
        mockDTO.name = new CountryDTO.NameDTO();
        mockDTO.name.official = "America";
        CountryDTO.CurrencyDTO mockCurrency = new CountryDTO.CurrencyDTO();
        mockCurrency = null;

       Country result = countryService.mapDtoToEntity(mockDTO);
       assertEquals("America" , result.names);
       assertEquals("none", result.currency ,"Should be 'none'");
    }

    @Test
    void DtoToEntity_Currencies_MAP_EMPTY(){
        CountryDTO mockDTO = new CountryDTO();
        mockDTO.name = new CountryDTO.NameDTO();
        mockDTO.name.official = "America";
        Country result = countryService.mapDtoToEntity(mockDTO);
        assertEquals("America",result.names);
        assertEquals("none" , result.currency,"Should be 'none'"); }

    CountryMapper mapper = new CountryMapperImpl();
    @Test
    void DtoToEntinty_SOAP(){

        TCountryCodeAndName soapCountry = new TCountryCodeAndName();
        soapCountry.setSISOCode("GR");
        soapCountry.setSName("Greece");
        SoapCallResponseDTO result = mapper.toSoapCountryDTO(soapCountry);
        assertNotNull(result );
        assertEquals("GR",result.getIsoCode(),"Bad ISO mapping");
        assertEquals("Greece" , result.getName(),"Bad name mapping");
    }}

