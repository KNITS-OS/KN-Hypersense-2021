package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactoryMapperTest {

    private FactoryMapper factoryMapper;

    @BeforeEach
    public void setUp() {
        factoryMapper = new FactoryMapperImpl();
    }
}
