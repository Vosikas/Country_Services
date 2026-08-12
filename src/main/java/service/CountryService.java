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
import util.StringUtils;
import java.util.List;

@ApplicationScoped
public class CountryService {
    @Inject
    @RestClient
    CountryClient countryClient;
    private static final Logger LOG = Logger.getLogger(CountryService.class);
    @Transactional
    public void fetchAndSaveCountries() {
        if (areCountriesLoaded()) {
            Log.info("Data is already loaded in the Country Database");
            return;
        }

        try {
            List<CountryDTO> fetchedCountries = fetchAndDeserializeCountries();
            saveCountries(fetchedCountries);

            Log.info("Successfully loaded " + Country.count() + " countries.");
        } catch (Exception e) {
            Log.error("Failed to hit API " + e.getMessage());
            throw new GenericErrorException("Internal error while fetching and saving countries: " + e.getMessage());
        }
    }

    public Country mapDtoToEntity(CountryDTO dto) {
        Country country = new Country();
        country.name = dto.getName().getOfficial();
        if (dto.getCurrencies() != null && !dto.getCurrencies().isEmpty()) {
            country.currency = dto.getCurrencies().get(0).getName();
        } else {
            country.currency = "none";
        }
        return country;
    }

    public List<Country> getCountries(int page, int size) {
        List<Country> countriesList = Country.findAll()
                .page(page, size)
                .list();
        if (countriesList.isEmpty()) {
            throw new GenericErrorException("No countries were found");
        }
        return countriesList;
    }

    public Country findCountry(String names) throws CountryNotFoundException {
        String queryName = StringUtils.stringToLower(names);
        Country country = Country.find("LOWER(name) LIKE ?1", "%" + queryName + "%").firstResult();
        if (country == null) {
            throw new CountryNotFoundException("Country with name " + queryName + " was not found");
        }
        return country;
    }

    public List<Country> findCurrencyCode(String currency) {
        List<Country> countryList = Country.list("currency", currency);
        if (countryList.isEmpty()) {
            throw new CountryNotFoundException("There are no countries with " + currency + " as their currency");
        }
        return countryList;
    }

    private boolean areCountriesLoaded() {
        return Country.count() > 0;
    }

    private List<CountryDTO> fetchAndDeserializeCountries() throws Exception {
        String rawJson = countryClient.fetchCountries("names,currencies");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode objectNode = mapper.readTree(rawJson)
                .path("data")
                .path("objects");
        return mapper.readValue(
                objectNode.toString(),
                mapper.getTypeFactory().constructCollectionType(List.class, CountryDTO.class)
        );
    }

    private void saveCountries(List<CountryDTO> countryDtos) {
        for (CountryDTO dto : countryDtos) {
            Country country = mapDtoToEntity(dto);
            if (country != null) {
                country.persist();
            }
        }
    }

}