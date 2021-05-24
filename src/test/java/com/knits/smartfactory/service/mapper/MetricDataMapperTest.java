package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetricDataMapperTest {

    private MetricDataMapper metricDataMapper;

    @BeforeEach
    public void setUp() {
        metricDataMapper = new MetricDataMapperImpl();
    }
}
