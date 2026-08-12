package mapper;

import db.Country;
import dto.CountryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface CurrencyMapper {
    @Mapping(target = "name", source = "currency")
    CountryDTO.Currency fromCountryCurrency(Country country);


}