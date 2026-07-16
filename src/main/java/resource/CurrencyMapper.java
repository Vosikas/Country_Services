package resource;


import DTO.CountriesDTO;
import db.Countries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);
    @Mapping(source ="currency" , target ="name")
    CountriesDTO.CurrencyDTO currencyToCurrencyDto(Countries countries);
}
