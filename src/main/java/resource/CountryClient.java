package resource;

import DTO.CountriesDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;



@RegisterRestClient(baseUri = "https://restcountries.com/v5" , configKey = "country-api")
public interface CountryClient {
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    List<CountriesDTO> fetchCountries(@QueryParam("fields") String fields);
}
