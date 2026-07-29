package mapper;
import dto.ErrorResponseBodyDTO;
import exceptions.CountryLoadException;
import exceptions.CountryNotFoundException;
import exceptions.GenericErrorException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionsMapper {
    @ServerExceptionMapper
    public RestResponse<ErrorResponseBodyDTO> mapCountryNotFoundException(CountryNotFoundException ex){
            ErrorResponseBodyDTO error = new ErrorResponseBodyDTO(ex.getMessage(),404);
            return RestResponse.status(Response.Status.NOT_FOUND , error);
    }

    @ServerExceptionMapper
    public RestResponse<ErrorResponseBodyDTO> mapCountryLoadException(CountryLoadException ex){
        ErrorResponseBodyDTO error = new ErrorResponseBodyDTO(ex.getMessage() ,500);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR , error);
    }

    @ServerExceptionMapper
    public RestResponse<ErrorResponseBodyDTO> mapGeneric_Throwable(GenericErrorException ex){
        ErrorResponseBodyDTO error = new ErrorResponseBodyDTO("An unexpected error occurred: " + ex.getMessage() , 500 );
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR , error);
    }
}
