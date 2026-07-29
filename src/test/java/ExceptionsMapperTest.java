import dto.ErrorResponseBodyDTO;
import exceptions.CountryLoadException;
import exceptions.CountryNotFoundException;
import exceptions.GenericErrorException;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mapper.ExceptionsMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionsMapperTest {
    private ExceptionsMapper exceptionsMapper;

    @BeforeEach
    void setUP(){exceptionsMapper = new ExceptionsMapper();}

    @Test
    void TestCountryNotFoundException(){
        CountryNotFoundException ex = new CountryNotFoundException("Country not found");
        RestResponse<ErrorResponseBodyDTO> response = exceptionsMapper.mapCountryNotFoundException(ex);
        ErrorResponseBodyDTO error = response.getEntity();
        assertEquals("Country not found" ,error.getMessage());
        assertEquals(404,error.getStatusCode());}

    @Test
    void TestCountryNotLoadException(){
        CountryLoadException ex = new CountryLoadException("Country not load");
        RestResponse<ErrorResponseBodyDTO> response = exceptionsMapper.mapCountryLoadException(ex);
        ErrorResponseBodyDTO error = response.getEntity();
        assertEquals("Country not load",error.getMessage());
        assertEquals(500,error.getStatusCode());

    }

    @Test
    void TestGeneric_Throwable() {
        GenericErrorException ex = new GenericErrorException("An unexpected error occurred");
        RestResponse<ErrorResponseBodyDTO> response = exceptionsMapper.mapGeneric_Throwable(ex);
        ErrorResponseBodyDTO error = response.getEntity();
        assertEquals("An unexpected error occurred", error.getMessage());
        assertEquals(500, error.getStatusCode());
    }

    }





