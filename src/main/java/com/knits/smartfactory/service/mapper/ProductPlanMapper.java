package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.ProductPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductPlan} and its DTO {@link ProductPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductPlanMapper extends EntityMapper<ProductPlanDTO, ProductPlan> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductPlanDTO toDtoId(ProductPlan productPlan);
}
