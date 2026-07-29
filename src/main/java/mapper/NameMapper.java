package mapper;
import dto.CountryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta")
public interface NameMapper {
    NameMapper INSTANCE = Mappers.getMapper(NameMapper.class);
    @Mapping(source="names" , target = "official")
    CountryDTO.NameDTO nameToDtoName(String names);
}
