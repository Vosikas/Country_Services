package resource;

import DTO.CountriesDTO;
import db.Countries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(uses = {NameMapper.class})
public interface CountriesMapper {
        CountriesDTO toDto(Countries entity);
        List<CountriesDTO> toDtoList(List<Countries> entities);

}
