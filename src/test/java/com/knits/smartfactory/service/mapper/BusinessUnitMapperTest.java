package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessUnitMapperTest {

    private BusinessUnitMapper businessUnitMapper;

    @BeforeEach
    public void setUp() {
        businessUnitMapper = new BusinessUnitMapperImpl();
    }
}
