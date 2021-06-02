package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductionLineGroupMapperTest {

    private ProductionLineGroupMapper productionLineGroupMapper;

    @BeforeEach
    public void setUp() {
        productionLineGroupMapper = new ProductionLineGroupMapperImpl();
    }
}
