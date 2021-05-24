package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.ProductionLineGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductionLineGroup} and its DTO {@link ProductionLineGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = { FactoryMapper.class })
public interface ProductionLineGroupMapper extends EntityMapper<ProductionLineGroupDTO, ProductionLineGroup> {
    @Mapping(target = "factory", source = "factory", qualifiedByName = "id")
    ProductionLineGroupDTO toDto(ProductionLineGroup s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductionLineGroupDTO toDtoId(ProductionLineGroup productionLineGroup);
}
