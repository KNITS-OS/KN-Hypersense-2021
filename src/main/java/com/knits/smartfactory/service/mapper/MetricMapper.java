package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.MetricDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Metric} and its DTO {@link MetricDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MetricMapper extends EntityMapper<MetricDTO, Metric> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetricDTO toDtoId(Metric metric);
}
