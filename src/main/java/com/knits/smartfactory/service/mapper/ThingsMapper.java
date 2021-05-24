package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.ThingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Things} and its DTO {@link ThingsDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductionLineMapper.class })
public interface ThingsMapper extends EntityMapper<ThingsDTO, Things> {
    @Mapping(target = "productionLine", source = "productionLine", qualifiedByName = "id")
    ThingsDTO toDto(Things s);
}
