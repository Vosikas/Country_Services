package resource;


import DTO.CurrencyDTO;
import db.Countries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);
    @Mapping(source ="currency" , target ="name")
    CurrencyDTO currencyToCurrencyDto(Countries countries);
}
