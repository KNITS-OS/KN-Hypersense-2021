package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.ProductionLineDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductionLine} and its DTO {@link ProductionLineDTO}.
 */
@Mapper(componentModel = "spring", uses = { LocationDataMapper.class, ProductionLineGroupMapper.class })
public interface ProductionLineMapper extends EntityMapper<ProductionLineDTO, ProductionLine> {
    @Mapping(target = "locationData", source = "locationData", qualifiedByName = "id")
    @Mapping(target = "productionLineGroup", source = "productionLineGroup", qualifiedByName = "id")
    ProductionLineDTO toDto(ProductionLine s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductionLineDTO toDtoId(ProductionLine productionLine);
}
