package mapper;

import dto.CountryDTO;
import dto.SoapCallCountryResponseDTO;
import db.Country;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.inject.Default;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.oorsprong.websamples.TCountryCodeAndName;
import java.util.List;

@Mapper(componentModel = "jakarta", uses = {NameMapper.class})
public interface CountryMapper {
        CountryDTO ToCountryDto(Country entity);

        List<CountryDTO> toCountryDtoList(List<Country> entities);

        @Mapping(source = "SISOCode", target = "isoCode")
        @Mapping(source = "SName", target = "name")
        SoapCallCountryResponseDTO toSoapCountryDTO(TCountryCodeAndName soapCountry);
        
        List<SoapCallCountryResponseDTO> toSoapCountryDTOList(List<TCountryCodeAndName> soapCountries);

        default Country mapDtoToEntity(CountryDTO dto) {
                Country country = new Country();
                country.name = dto.getName().getOfficial();
                if (dto.getCurrencies() != null && !dto.getCurrencies().isEmpty()) {
                        country.currency = dto.getCurrencies().get(0).getName();
                } else {
                        country.currency = "none";
                }
                return country;
        }
}

