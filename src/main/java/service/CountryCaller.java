package service;
import io.quarkus.logging.Log;
import org.jboss.logging.Logger;
import DTO.CountriesDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.Countries;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import resource.CountryClient;

import java.util.List;



@ApplicationScoped
public class CountryCaller {
    private static final Logger LOG = Logger.getLogger(CountryCaller.class);
    @Inject
    @RestClient
    CountryClient countryClient;


    @Transactional
    public void fetchAndSaveCountries() {
        if (Countries.count() > 0) {
            Log.info("System already full");
            return;
        }

        try {

            String RawJSON  = countryClient.fetchCountries("names,currencies");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode objectNode = mapper.readTree(RawJSON)
                    .path("data")
                    .path("objects");
            List<CountriesDTO> fetchData = mapper.readValue(
                    objectNode.toString(),
                    mapper.getTypeFactory().constructCollectionType(List.class, CountriesDTO.class)
            );
            Log.info("Το API έφερε συνολικά: " + fetchData.size() + " χώρες.");
            for (CountriesDTO dto : fetchData) {

                Countries country = mapDtoToEntity(dto);
                if (country == null){
                    continue;
                }
                country.persist();
            }
            Log.info("Succesfully loaded " + Countries.count() + " countries.");

        } catch (Exception e) {
            Log.error("Failed to hit API " + e.getMessage());
        }

    }


    public Countries mapDtoToEntity(CountriesDTO dto) {
        Countries country = new Countries();
        country.names = dto.names.official;
        if (dto.currencies != null && !dto.currencies.isEmpty()) {

            country.currency = dto.currencies.get(0).name;
        } else {
            country.currency = "none";
        }

        return country;
    }



    public List<Countries> getCountries(int page , int size){
        return Countries.findAll()
                .page(page,size)
                .list();
    }
    public Countries FindCountry(String names){
        String query_name = STR."%\{names.toLowerCase()}%";
        System.out.println("ΨΑΧΝΩ ΓΙΑ: [" + query_name + "]");
        return Countries.find("LOWER(names) LIKE ?1", query_name).firstResult();
    }
    public List<Countries> FindCurrencyCode(String currency){
        return Countries.list("currency" , currency);
    }


}
