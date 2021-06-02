package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductDataMapperTest {

    private ProductDataMapper productDataMapper;

    @BeforeEach
    public void setUp() {
        productDataMapper = new ProductDataMapperImpl();
    }
}
