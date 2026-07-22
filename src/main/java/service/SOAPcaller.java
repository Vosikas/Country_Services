package service;

import DTO.CountriesDTO;
import db.Countries;
import io.quarkiverse.cxf.annotation.CXFClient;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.oorsprong.websamples.ArrayOftCountryCodeAndName;
import org.oorsprong.websamples.TCountryCodeAndName;
import org.oorsprong.websamples.TCountryInfo;
import org.oorsprong.websamples_countryinfo.CountryInfoServiceSoapType;

import java.util.List;

@ApplicationScoped
public class SOAPcaller {
    @Inject
    @CXFClient("countryClient")
    CountryInfoServiceSoapType soapService;

    public String getCountryByIso(String ISO){
        TCountryInfo country = soapService.fullCountryInfo(ISO);
        return country.getSName();
    }
    @CacheResult(cacheName = "countries - list")
    public ArrayOftCountryCodeAndName getAllCountries() {
        return soapService.listOfCountryNamesByCode();
    }
}
