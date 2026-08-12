package mapper;

import db.Country;
import dto.CountryDTO;
import dto.SoapCallCountryResponseDTO;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.oorsprong.websamples.TCountryCodeAndName;
import service.CountryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.wildfly.common.Assert.assertNotNull;
class CountryMapperTest  {
    CountryService countryService = new CountryService();
    @Nested
    @DisplayName("Happy Paths - Successful Operations")
    class HappyPaths{
    @Test
    void DtoToEntity_AllFields() {
        CountryDTO mockDTO = new CountryDTO();
        mockDTO.setName(new CountryDTO.Name());
        mockDTO.getName().setOfficial("America");
        CountryDTO.Currency mockCurrency = new CountryDTO.Currency();
        mockCurrency.setName("Dollar");

        Map<String, CountryDTO.Currency> mockMAP = new HashMap<>();
        mockMAP.put("Dollar",mockCurrency);
        List<CountryDTO.Currency> mockList = new ArrayList<>();
        mockList.add(mockCurrency);
        mockDTO.setCurrencies(mockList);
        Country result = countryService.mapDtoToEntity(mockDTO);
        assertEquals("America", result.name);
        assertEquals("Dollar", result.currency);
    }

    CountryMapper mapper = new CountryMapperImpl();
    @Test
    void DtoToCountryEntity_SOAP(){
        TCountryCodeAndName soapCountry = new TCountryCodeAndName();
        soapCountry.setSISOCode("GR");
        soapCountry.setSName("Greece");
        SoapCallCountryResponseDTO result = mapper.toSoapCountryDTO(soapCountry);
        assertNotNull(result );
        assertEquals("GR",result.getIsoCode(),"Bad ISO mapping");
        assertEquals("Greece" , result.getName(),"Bad name mapping");
    }}

    @Nested
    @DisplayName("Unhappy Paths - Error handling")
    class UnhappyPaths{
    @Test
    void DtoToEntity_CurrenciesNull() {
        CountryDTO mockDTO = new CountryDTO();
        mockDTO.setName(new CountryDTO.Name());
        mockDTO.getName().setOfficial("America");
        CountryDTO.Currency mockCurrency = new CountryDTO.Currency();
        mockCurrency = null;
        Country result = countryService.mapDtoToEntity(mockDTO);
        assertEquals("America" , result.name);
        assertEquals("none", result.currency ,"Should be 'none'");
    }

    @Test
    void DtoToEntity_Currencies_MAP_EMPTY(){
        CountryDTO mockDTO = new CountryDTO();
        mockDTO.setName(new CountryDTO.Name());
        mockDTO.getName().setOfficial("America");
        Country result = countryService.mapDtoToEntity(mockDTO);
        assertEquals("America",result.name);
        assertEquals("none" , result.currency,"Should be 'none'"); }

    }}

