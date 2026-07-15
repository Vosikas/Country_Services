package service;


import DTO.CountriesDTO;
import db.Countries;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import resource.CountryClient;

import java.util.List;

@ApplicationScoped
public class StartupLoader {
    @Inject
    @RestClient
    CountryClient countryClient;


    public void putData(@Observes StartupEvent loader) {
        List<CountriesDTO> fetchData;
        if (Countries.count() > 0) {
            System.out.println("System already ready");
            return;
        } else {

            fetchData = countryClient.fetchCountries("name,currencies");
        }
        System.out.println("Data insertion starts now");
        for (CountriesDTO dto : fetchData) {
            Countries countries = new Countries();
            countries.name = dto.name.official;
            if(dto.currencies != null && !dto.currencies.isEmpty()){
                String currencyKey = dto.currencies.keySet().iterator().next();
                countries.currency = dto.currencies.get(currencyKey).name;
            }
            else{
                countries.currency = "none";
            }
            countries.persist();
        System.out.println(STR."Succesfull load of \{Countries.count()} countries");

        }
    }



}
