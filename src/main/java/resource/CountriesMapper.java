package resource;

import DTO.CountriesDTO;
import DTO.SoapDTO;
import db.Countries;
import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.oorsprong.websamples.TCountryCodeAndName;

import java.util.List;

@Mapper(componentModel = "jakarta", uses = {NameMapper.class})
public interface CountriesMapper {
        CountriesDTO toDto(Countries entity);
        List<CountriesDTO> toDtoList(List<Countries> entities);
        @Mapping(source = "SISOCode", target = "isoCode")
        @Mapping(source = "SName", target = "name")
        SoapDTO toSoapCountryDTO(TCountryCodeAndName soapCountry);
        List<SoapDTO> toSoapCountryDTOList(List<TCountryCodeAndName> soapCountries);
}
