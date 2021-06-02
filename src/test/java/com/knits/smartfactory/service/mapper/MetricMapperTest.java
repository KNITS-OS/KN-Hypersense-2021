package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetricMapperTest {

    private MetricMapper metricMapper;

    @BeforeEach
    public void setUp() {
        metricMapper = new MetricMapperImpl();
    }
}
