package resource;

import DTO.CountriesDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;



@RegisterRestClient(baseUri = "https://api.restcountries.com" , configKey = "country-api")
public interface CountryClient {
    @GET
    @Path("/countries/v5")
    @Produces(MediaType.APPLICATION_JSON)
    List<CountriesDTO> fetchCountries(@QueryParam("fields") String fields);
}
