package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationDataMapperTest {

    private LocationDataMapper locationDataMapper;

    @BeforeEach
    public void setUp() {
        locationDataMapper = new LocationDataMapperImpl();
    }
}
