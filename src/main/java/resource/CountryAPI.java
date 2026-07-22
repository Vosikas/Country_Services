package resource;


import DTO.CountriesDTO;
import DTO.SoapDTO;
import db.Countries;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import service.CountryCaller;
import service.SOAPcaller;

import java.util.List;

@Path("api/v1/countries")
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
    @Path("/{names}")
    public Countries getCountryByName(@PathParam("names") String names){
        return countryCaller.FindCountry(names);
    }

    @GET
    @Path("/currency/{currencyCode}")
    public List<Countries> findCountriesByCurrency(@PathParam("currencyCode") String currencyCode){
        return countryCaller.FindCurrencyCode(currencyCode);
    }
    @Inject
    SOAPcaller soapCaller;

    @Inject
    CountriesMapper mapper;
    @GET
    @Path("/soap/code/{code}")
    public String findCountryByISO(@PathParam("code") String code){
        return soapCaller.getCountryByIso(code);

    }

    @GET
    @Path("/soap/code")
    public List<SoapDTO> getAllCountriesSoap(){
        return mapper.toSoapCountryDTOList(soapCaller.getAllCountries().getTCountryCodeAndName());
    }


}
