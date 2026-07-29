package service;

import db.Country;
import exceptions.CountryLoadException;
import exceptions.CountryNotFoundException;
import io.quarkiverse.cxf.annotation.CXFClient;
import io.quarkus.cache.CacheResult;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.oorsprong.websamples.ArrayOftCountryCodeAndName;
import org.oorsprong.websamples.TCountryInfo;
import org.oorsprong.websamples_countryinfo.CountryInfoServiceSoapType;

import java.util.List;

@ApplicationScoped
public class SoapService {
    @Inject
    @CXFClient("countryClient")
    CountryInfoServiceSoapType soapService;

    public String getCountryByIso(String ISO){
        TCountryInfo country = soapService.fullCountryInfo(ISO);
        Log.info("THE NAME IS " + country.getSName());
        if(country==null || country.getSName().equals("Country not found in the database")){
            throw new CountryNotFoundException("Country with ISO code: " + ISO + " was not found");
        }
        return country.getSName();
    }
    @CacheResult(cacheName = "countries - list")
    public ArrayOftCountryCodeAndName getAllCountries() {
        ArrayOftCountryCodeAndName soapCountryList = soapService.listOfCountryNamesByCode();
        if(soapCountryList==null || soapCountryList.getTCountryCodeAndName().isEmpty() || soapCountryList.getTCountryCodeAndName() == null  ){
            throw new CountryLoadException("Countries couldn't load properly");
        }
        return soapCountryList;
    }
}
