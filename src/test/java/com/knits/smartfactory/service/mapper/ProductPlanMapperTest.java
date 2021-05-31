package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductPlanMapperTest {

    private ProductPlanMapper productPlanMapper;

    @BeforeEach
    public void setUp() {
        productPlanMapper = new ProductPlanMapperImpl();
    }
}
