package resource;


import DTO.CountriesDTO;
import db.Countries;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import service.CountryCaller;

import java.util.List;

@Path("api/countries")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CountryAPI {
    @Inject
    CountryCaller countryCaller;

    @GET
    public List<Countries> getAllCountries(@QueryParam("page")@DefaultValue("0") int page , @QueryParam("size") @DefaultValue("10") int size){
        return countryCaller.getCountries(page,size);
    }

    @GET
    @Path("/{name}")
    public Countries getCountryByName(@PathParam("name") String name){
        return countryCaller.FindCountry(name);
    }

    @GET
    @Path("/currency/{currencyCode}")
    public List<Countries> finCountriesByCurrency(@PathParam("currencyCode") String currencyCode){
        return countryCaller.FindCurrencyCode(currencyCode);
    }


}
