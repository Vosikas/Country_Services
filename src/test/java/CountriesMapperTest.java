import db.Country;
import dto.CountryDTO;
import dto.SoapCallCountryResponseDTO;
import mapper.CountryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.oorsprong.websamples.TCountryCodeAndName;
import mapper.CountryMapperImpl;
import service.CountryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.wildfly.common.Assert.assertNotNull;
class CountryMapperTest extends CountryService {
    private CountryService countryService;
    @BeforeEach
    void setUP(){
        countryService = new CountryService();
    }

    @Test
    void DtoToEntity_AllFields() {
        CountryDTO mockDTO = new CountryDTO();
        mockDTO.setName(new CountryDTO.Name());
        mockDTO.getName().setOfficial("America");
        CountryDTO.CurrencyDTO mockCurrency = new CountryDTO.CurrencyDTO();
        mockCurrency.setName("Dollar");

        Map<String, CountryDTO.CurrencyDTO> mockMAP = new HashMap<>();
        mockMAP.put("Dollar",mockCurrency);
        List<CountryDTO.CurrencyDTO> mockList = new ArrayList<>();
        mockList.add(mockCurrency);
        mockDTO.setCurrencies(mockList);
        Country result = mapDtoToEntity(mockDTO);
        assertEquals("America", result.name);
        assertEquals("Dollar", result.currency);
    }

    @Test
    void DtoToEntity_CurrenciesNull() {
        CountryDTO mockDTO = new CountryDTO();
        mockDTO.setName(new CountryDTO.Name());
        mockDTO.getName().setOfficial("America");
        CountryDTO.CurrencyDTO mockCurrency = new CountryDTO.CurrencyDTO();
        mockCurrency = null;
        Country result = mapDtoToEntity(mockDTO);
        assertEquals("America" , result.name);
        assertEquals("none", result.currency ,"Should be 'none'");
    }

    @Test
    void DtoToEntity_Currencies_MAP_EMPTY(){
        CountryDTO mockDTO = new CountryDTO();
        mockDTO.setName(new CountryDTO.Name());
        mockDTO.getName().setOfficial("America");
        Country result = mapDtoToEntity(mockDTO);
        assertEquals("America",result.name);
        assertEquals("none" , result.currency,"Should be 'none'"); }

    CountryMapper mapper = new CountryMapperImpl();
    @Test
    void DtoToEntinty_SOAP(){
        TCountryCodeAndName soapCountry = new TCountryCodeAndName();
        soapCountry.setSISOCode("GR");
        soapCountry.setSName("Greece");
        SoapCallCountryResponseDTO result = mapper.toSoapCountryDTO(soapCountry);
        assertNotNull(result );
        assertEquals("GR",result.getIsoCode(),"Bad ISO mapping");
        assertEquals("Greece" , result.getName(),"Bad name mapping");
    }}

