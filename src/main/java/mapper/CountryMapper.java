package mapper;

import dto.CountryDTO;
import dto.SoapCallResponseDTO;
import db.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.oorsprong.websamples.TCountryCodeAndName;
import java.util.List;

@Mapper(componentModel = "jakarta", uses = {NameMapper.class})
public interface CountryMapper {
        CountryDTO toDto(Country entity);
        List<CountryDTO> toDtoList(List<Country> entities);
        @Mapping(source = "SISOCode", target = "isoCode")
        @Mapping(source = "SName", target = "name")
        SoapCallResponseDTO toSoapCountryDTO(TCountryCodeAndName soapCountry);
        List<SoapCallResponseDTO> toSoapCountryDTOList(List<TCountryCodeAndName> soapCountries);
}
