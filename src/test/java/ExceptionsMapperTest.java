import DTO.ErrorsDTO;
import com.google.inject.internal.Errors;
import com.google.inject.internal.GenericErrorDetail;
import exceptions.CountryLoadException;
import exceptions.CountryNotFoundException;
import exceptions.Generic_Throwable;
import io.smallrye.classfile.impl.AbstractAttributeMapper;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resource.ExceptionsMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionsMapperTest {
    private ExceptionsMapper exceptionsMapper;

    @BeforeEach
    void setUP(){exceptionsMapper = new ExceptionsMapper();}

    @Test
    void TestCountryNotFoundException(){
        CountryNotFoundException ex = new CountryNotFoundException("Country not found");
        RestResponse<ErrorsDTO> response = exceptionsMapper.mapCountryFoundException(ex);
        ErrorsDTO error = response.getEntity();
        assertEquals("Country not found" ,error.getMessage());
        assertEquals(404,error.getStatusCode());}

    @Test
    void TestCountryNotLoadException(){
        CountryLoadException ex = new CountryLoadException("Country not load");
        RestResponse<ErrorsDTO> response = exceptionsMapper.mapCountryLoadException(ex);
        ErrorsDTO error = response.getEntity();
        assertEquals("Country not load",error.getMessage());
        assertEquals(500,error.getStatusCode());

    }

    @Test
    void TestGeneric_Throwable() {
        Generic_Throwable ex = new Generic_Throwable("An unexpected error occured");
        RestResponse<ErrorsDTO> response = exceptionsMapper.mapGeneric_Throwable(ex);
        ErrorsDTO error = response.getEntity();
        assertEquals("An unexpected error occurred", error.getMessage());
        assertEquals(500, error.getStatusCode());
    }

    }





