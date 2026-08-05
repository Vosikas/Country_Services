package mapper;

import dto.CountryDTO;
import dto.SoapCallCountryResponseDTO;
import db.Country;
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
}
