package mapper;

import dto.CountryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.*;

public class NameMapperTest {
    private final NameMapper nameMapper = Mappers.getMapper(NameMapper.class);

@Nested
@DisplayName("Happy cases - Successful operations")
class HappyPath{
    @Test
    public void testFromCountryNameToCountryDtoName() {
        String inputName = "Hellenic Republic";
        CountryDTO.Name nameDto = nameMapper.fromCountryName(inputName);
        assertNotNull(nameDto, "Name should not be null");
        assertEquals("Hellenic Republic", nameDto.getOfficial(), "Name should be registered correctly");
    }}
@Nested
@DisplayName("Unhappy cases - Error Handling")
class UnhappyPath{
    @Test
    public void testFromCountryNameWithNullReturnsNull() {
        CountryDTO.Name nameDto = nameMapper.fromCountryName(null);
        assertNull(nameDto, "If String is null,return null");
    }}
}