package com.knits.smartfactory.service.mapper;

import com.knits.smartfactory.domain.*;
import com.knits.smartfactory.service.dto.CompanyUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyUser} and its DTO {@link CompanyUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserProfileMapper.class, BusinessUnitMapper.class })
public interface CompanyUserMapper extends EntityMapper<CompanyUserDTO, CompanyUser> {
    @Mapping(target = "userProfile", source = "userProfile", qualifiedByName = "id")
    @Mapping(target = "businessUnit", source = "businessUnit", qualifiedByName = "id")
    CompanyUserDTO toDto(CompanyUser s);
}
