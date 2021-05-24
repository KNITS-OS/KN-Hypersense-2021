package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThingsMapperTest {

    private ThingsMapper thingsMapper;

    @BeforeEach
    public void setUp() {
        thingsMapper = new ThingsMapperImpl();
    }
}
