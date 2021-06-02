package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.LocationDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationData} and its DTO {@link LocationDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationDataMapper extends EntityMapper<LocationDataDTO, LocationData> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDataDTO toDtoId(LocationData locationData);
}
