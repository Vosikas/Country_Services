package mapper;

import dto.CountryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface NameMapper {
    @Mapping(source = "names", target = "official")
    CountryDTO.Name fromCountryName(String names);
}