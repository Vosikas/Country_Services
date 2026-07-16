package service;


import DTO.CountriesDTO;
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

    @Inject
    @RestClient
    CountryClient countryClient;


    @Transactional
    public void fetchAndSaveCountries() {
        if (Countries.count() > 0) {
            System.out.println("System already full");
            return;
        }



        try {
            List<CountriesDTO> fetchData = countryClient.fetchCountries("name,currencies");

            for (CountriesDTO dto : fetchData) {

                Countries country = mapDtoToEntity(dto);
                if (country == null){
                    continue;
                }
                country.persist();
            }
            System.out.println("Succesfully loaded " + Countries.count() + " countries.");

        } catch (Exception e) {
            System.err.println("Failed to hit API " + e.getMessage());
        }
    }


    public Countries mapDtoToEntity(CountriesDTO dto) {
        Countries country = new Countries();
        country.name = dto.name.official;
        if (dto.currencies != null && !dto.currencies.isEmpty()) {
            String currencyKey = dto.currencies.keySet().iterator().next();
            country.currency = dto.currencies.get(currencyKey).name;
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
    public Countries FindCountry(String name){
        String query_name = STR."%\{name}%";
        return Countries.find("name ILIKE ?1", query_name).firstResult();
    }
    public List<Countries> FindCurrencyCode(String currency){
        return Countries.list("currency" , currency);
    }

}
