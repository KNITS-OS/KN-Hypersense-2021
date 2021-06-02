package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.BusinessUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessUnit} and its DTO {@link BusinessUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = { FactoryMapper.class })
public interface BusinessUnitMapper extends EntityMapper<BusinessUnitDTO, BusinessUnit> {
    @Mapping(target = "factory", source = "factory", qualifiedByName = "id")
    BusinessUnitDTO toDto(BusinessUnit s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BusinessUnitDTO toDtoId(BusinessUnit businessUnit);
}
