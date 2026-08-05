package mapper;

import db.Country;
import dto.CountryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyMapperTest {
    private final CurrencyMapper currencyMapper = Mappers.getMapper(CurrencyMapper.class);

    @Nested
    @DisplayName("Happy Paths - Successful Operations")
    class HappyPaths {
    @Test
    public void testFromCountryCurrencyToCountryDtoCurrencyName() {
        Country country = new Country();
        country.name = "Greece";
        country.currency = "Euro";
        CountryDTO.Currency currency = currencyMapper.fromCountryCurrency(country);
        assertNotNull(currency, "The mapped CurrencyDTO should not be null");
        assertEquals("Euro", currency.getName(), "Currency name should match the entity's currency field");
    }}

    @Nested
    @DisplayName("Unhappy Paths - Error handling")
    class UnhappyPaths{
    @Test
    public void testFromCountryCurrencyWithNullEntityToNullAnswer() {
        CountryDTO.Currency currency = currencyMapper.fromCountryCurrency(null);
        assertNull(currency, "Mapping a null Country entity should return null");
    }
}
}