package resource;


import DTO.ErrorsDTO;
import exceptions.CountryLoadException;
import exceptions.CountryNotFoundException;
import exceptions.Generic_Throwable;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExtensionsMapper {
    @ServerExceptionMapper
    public RestResponse<ErrorsDTO> mapCountryFoundException(CountryNotFoundException ex){
            ErrorsDTO error = new ErrorsDTO(ex.getMessage() ,404);
            return RestResponse.status(Response.Status.NOT_FOUND , error);
    }

    @ServerExceptionMapper
    public RestResponse<ErrorsDTO> mapCountryLoadException(CountryLoadException ex){
        ErrorsDTO error = new ErrorsDTO(ex.getMessage() ,500);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR , error);
    }

    @ServerExceptionMapper
    public RestResponse<ErrorsDTO> mapGeneric_Throwable(Generic_Throwable ex){
        ErrorsDTO error = new ErrorsDTO("An unexpected error occurred." , 500 );
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR , error);
    }
}
