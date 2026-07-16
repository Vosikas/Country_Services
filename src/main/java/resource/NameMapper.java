package resource;



import DTO.CountriesDTO;
import db.Countries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NameMapper {
    NameMapper INSTANCE = Mappers.getMapper(NameMapper.class);

    @Mapping(source="name" , target = "official")
    CountriesDTO.NameDTO nameToDtoName(String name);
}
