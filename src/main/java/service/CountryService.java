package service;
import db.Country;
import dto.CountryDTO;
import exceptions.CountryNotFoundException;
import exceptions.GenericErrorException;
import io.quarkus.logging.Log;
import org.jboss.logging.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import client.CountryClient;
import java.util.List;

@ApplicationScoped
public class CountryService {
    private static final Logger LOG = Logger.getLogger(CountryService.class);
    @Inject
    @RestClient
    CountryClient countryClient;

    @Transactional
    public void fetchAndSaveCountries()  {
        if (Country.count() > 0) {
            Log.info("System already full");
            return;
        }
        try {
            String RawJSON  = countryClient.fetchCountries("names,currencies");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode objectNode = mapper.readTree(RawJSON)
                    .path("data")
                    .path("objects");
            List<CountryDTO> fetchData = mapper.readValue(
                    objectNode.toString(),
                    mapper.getTypeFactory().constructCollectionType(List.class, CountryDTO.class)
            );
            Log.info("Το API έφερε συνολικά: " + fetchData.size() + " χώρες.");
            for (CountryDTO dto : fetchData) {

                Country country = mapDtoToEntity(dto);
                if (country == null){
                    continue;
                }
                country.persist();
            }
            Log.info("Succesfully loaded " + Country.count() + " countries.");
        } catch (Exception e) {
            Log.error("Failed to hit API " + e.getMessage());
            throw new GenericErrorException("Internal error while fetching and saving countries");
        }
    }

    public Country mapDtoToEntity(CountryDTO dto) {
        Country country = new Country();
        country.names = dto.name.official;
        if (dto.currencies != null && !dto.currencies.isEmpty()) {

            country.currency = dto.currencies.get(0).name;
        } else {
            country.currency = "none";
        }

        return country;
    }

    public List<Country> getCountries(int page , int size){
        List<Country> countriesList = Country.findAll()
                .page(page,size)
                .list();
        if (countriesList.isEmpty()){
            throw new GenericErrorException("No countries were found");}
        return countriesList;
    }
    public Country FindCountry(String names) throws CountryNotFoundException {
        String query_name = STR."%\{names.toLowerCase()}%";
        Country country =  Country.find("LOWER(names) LIKE ?1", query_name).firstResult();
        if(country == null){
            throw new CountryNotFoundException("Country with name "+ query_name + " was not found");
        }
        return country;
    }
    public List<Country> FindCurrencyCode(String currency){
        List<Country> countryList =Country.list("currency" , currency);
        if(countryList.isEmpty()){
            throw new CountryNotFoundException("There are no countries with " + currency + " as their currency");
        }
        return countryList;
    }
}
