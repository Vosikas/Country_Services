package resource;
import db.Country;
import dto.SoapCallResponseDTO;
import io.quarkus.cache.CacheResult;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import mapper.CountryMapper;
import service.CountryService;
import service.SoapService;

import java.util.List;

@Path("api/v1/countries")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CountryResource {

    @Inject
    CountryService countryService;
    @Inject
    SoapService soapCaller;
    @Inject
    CountryMapper mapper;

    @GET
    @CacheResult(cacheName = "rest-countries-list")
    public List<Country> getAllCountries(@QueryParam("page")@DefaultValue("0") int page , @QueryParam("size") @DefaultValue("10") int size){
        return countryService.getCountries(page,size);
    }

    @GET
    @Path("/{names}")
    public Country getCountryByName(@PathParam("names") String names){
        return countryService.FindCountry(names);
    }

    @GET
    @Path("/currency/{currencyCode}")
    public List<Country> findCountriesByCurrency(@PathParam("currencyCode") String currencyCode){
        return countryService.FindCurrencyCode(currencyCode);
    }

    @GET
    @Path("/soap/code/{code}")
    public String findCountryByISO(@PathParam("code") String code){
        return soapCaller.getCountryByIso(code);
    }

    @GET
    @Path("/soap/code")
    public List<SoapCallResponseDTO> getAllCountriesSoap(){
        return mapper.toSoapCountryDTOList(soapCaller.getAllCountries().getTCountryCodeAndName());
    }
}
