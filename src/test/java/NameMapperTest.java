
import dto.CountryDTO;
import mapper.NameMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.*;

public class NameMapperTest {

    private final NameMapper nameMapper = Mappers.getMapper(NameMapper.class);

    @Test
    @DisplayName("Should correctly map a String to CountryDTO.Name.official")
    public void testToDtoName() {
        String inputName = "Hellenic Republic";
        CountryDTO.Name nameDto = nameMapper.toDtoName(inputName);
        assertNotNull(nameDto, "Το αντικείμενο Name δεν πρέπει να είναι null");
        assertEquals("Hellenic Republic", nameDto.getOfficial(), "Το όνομα πρέπει να περαστεί σωστά στο πεδίο official");
    }

    @Test
    @DisplayName("Should return null when input String is null")
    public void testToDtoNameWithNull() {
        CountryDTO.Name nameDto = nameMapper.toDtoName(null);
        assertNull(nameDto, "Αν το String είναι null, πρέπει να επιστραφεί null");
    }
}