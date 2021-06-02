package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.ProductionPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductionPlan} and its DTO {@link ProductionPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductionPlanMapper extends EntityMapper<ProductionPlanDTO, ProductionPlan> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductionPlanDTO toDtoId(ProductionPlan productionPlan);
}
