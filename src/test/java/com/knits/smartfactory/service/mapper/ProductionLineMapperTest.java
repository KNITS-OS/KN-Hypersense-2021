package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductionLineMapperTest {

    private ProductionLineMapper productionLineMapper;

    @BeforeEach
    public void setUp() {
        productionLineMapper = new ProductionLineMapperImpl();
    }
}
