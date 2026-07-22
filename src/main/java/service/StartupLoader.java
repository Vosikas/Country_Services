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

    CountryCaller countryCaller ;


    public void putData(@Observes StartupEvent loader) {
        System.out.println("Server started");
        countryCaller.fetchAndSaveCountries();

    }}


