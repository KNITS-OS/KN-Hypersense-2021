package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.ProductDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductData} and its DTO {@link ProductDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductDataMapper extends EntityMapper<ProductDataDTO, ProductData> {}
