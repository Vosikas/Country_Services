package util;
import io.quarkus.arc.profile.UnlessBuildProfile;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import service.CountryService;

@ApplicationScoped
@UnlessBuildProfile("test")
public class StartupLoader {
    @Inject
    CountryService countryService;
    private static final Logger LOG = Logger.getLogger(StartupLoader.class);
    public void putData(@Observes StartupEvent loader) {
        Log.info("Server started");
        countryService.fetchAndSaveCountries();
    }}


