package mapper;

import db.Country;
import dto.CountryDTO;
import dto.SoapCallCountryResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.oorsprong.websamples.TCountryCodeAndName;

import java.util.ArrayList;
import java.util.List;

// Σωστά imports του JUnit
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CountryMapperTest {

    // Το ορίζουμε μία φορά εδώ για να το βλέπουν όλα τα tests
    CountryMapper countryMapper = new CountryMapperImpl();

    @Nested
    @DisplayName("Happy Paths - Successful Operations")
    class HappyPaths {

        @Test
        void DtoToEntity_AllFields() {
            CountryDTO mockDTO = new CountryDTO();
            mockDTO.setName(new CountryDTO.Name());
            mockDTO.getName().setOfficial("America");

            CountryDTO.Currency mockCurrency = new CountryDTO.Currency();
            mockCurrency.setName("Dollar");

            List<CountryDTO.Currency> mockList = new ArrayList<>();
            mockList.add(mockCurrency);
            mockDTO.setCurrencies(mockList);

            Country result = countryMapper.mapDtoToEntity(mockDTO);

            assertEquals("America", result.name);
            assertEquals("Dollar", result.currency);
        }

        @Test
        void DtoToCountryEntity_SOAP() {
            TCountryCodeAndName soapCountry = new TCountryCodeAndName();
            soapCountry.setSISOCode("GR");
            soapCountry.setSName("Greece");

            SoapCallCountryResponseDTO result = countryMapper.toSoapCountryDTO(soapCountry);

            assertNotNull(result);
            assertEquals("GR", result.getIsoCode(), "Bad ISO mapping");
            assertEquals("Greece", result.getName(), "Bad name mapping");
        }
    }

    @Nested
    @DisplayName("Unhappy Paths - Error handling")
    class UnhappyPaths {

        @Test
        void DtoToEntity_CurrenciesNull() {
            CountryDTO mockDTO = new CountryDTO();
            mockDTO.setName(new CountryDTO.Name());
            mockDTO.getName().setOfficial("America");
            // Δεν αρχικοποιούμε το Currency list

            Country result = countryMapper.mapDtoToEntity(mockDTO);

            assertEquals("America", result.name);
            assertEquals("none", result.currency, "Should be 'none'");
        }

        @Test
        void DtoToEntity_Currencies_MAP_EMPTY() {
            CountryDTO mockDTO = new CountryDTO();
            mockDTO.setName(new CountryDTO.Name());
            mockDTO.getName().setOfficial("America");
            mockDTO.setCurrencies(new ArrayList<>()); // Άδεια λίστα

            Country result = countryMapper.mapDtoToEntity(mockDTO);

            assertEquals("America", result.name);
            assertEquals("none", result.currency, "Should be 'none'");
        }
    }
}