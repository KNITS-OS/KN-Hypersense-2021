package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductionLineMapper.class })
public interface StateMapper extends EntityMapper<StateDTO, State> {
    @Mapping(target = "productionLine", source = "productionLine", qualifiedByName = "id")
    StateDTO toDto(State s);
}
