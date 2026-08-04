

import db.Country;
import dto.CountryDTO;
import mapper.CurrencyMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyMapperTest {


    private final CurrencyMapper currencyMapper = Mappers.getMapper(CurrencyMapper.class);

    @Test
    @DisplayName("Should correctly map Country.currency to CurrencyDTO.name")
    public void testToCurrencyDto() {
        Country country = new Country();
        country.name = "Greece";
        country.currency = "Euro";

        CountryDTO.CurrencyDTO currencyDto = currencyMapper.toCurrencyDto(country);

        assertNotNull(currencyDto, "The mapped CurrencyDTO should not be null");
        assertEquals("Euro", currencyDto.getName(), "Currency name should match the entity's currency field");
    }

    @Test
    @DisplayName("Should return null when input Country entity is null")
    public void testToCurrencyDtoWithNullEntity() {
        CountryDTO.CurrencyDTO currencyDto = currencyMapper.toCurrencyDto(null);

        assertNull(currencyDto, "Mapping a null Country entity should return null");
    }
}