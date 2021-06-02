package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductionPlanMapperTest {

    private ProductionPlanMapper productionPlanMapper;

    @BeforeEach
    public void setUp() {
        productionPlanMapper = new ProductionPlanMapperImpl();
    }
}
