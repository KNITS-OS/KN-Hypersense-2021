package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.FactoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Factory} and its DTO {@link FactoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FactoryMapper extends EntityMapper<FactoryDTO, Factory> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FactoryDTO toDtoId(Factory factory);
}
