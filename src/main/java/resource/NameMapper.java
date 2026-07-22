package resource;



import DTO.CountriesDTO;
import db.Countries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta")
public interface NameMapper {
    NameMapper INSTANCE = Mappers.getMapper(NameMapper.class);

    @Mapping(source="names" , target = "official")
    CountriesDTO.NameDTO nameToDtoName(String names);
}
