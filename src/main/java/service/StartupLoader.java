package service;


import DTO.CountriesDTO;
import db.Countries;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import resource.CountryClient;

import java.util.List;
import org.jboss.logging.Logger;

@ApplicationScoped
public class StartupLoader {
    @Inject
    CountryCaller countryCaller ;
    private static final Logger LOG = Logger.getLogger(StartupLoader.class);
    public void putData(@Observes StartupEvent loader) {
        Log.info("Server started");
        countryCaller.fetchAndSaveCountries();

    }}


