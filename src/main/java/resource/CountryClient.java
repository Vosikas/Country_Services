package resource;

import DTO.CountriesDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;



@RegisterRestClient(baseUri = "https://api.restcountries.com" , configKey = "country-api")
@ClientHeaderParam(name = "Authorization", value = "${api.external.auth-token}")
public interface CountryClient {
    @GET
    @Path("/countries/v5")
    @Produces(MediaType.APPLICATION_JSON)
    String fetchCountries(@QueryParam("fields") String fields);
}
