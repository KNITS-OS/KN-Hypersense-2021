package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetricDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetricData.class);
        MetricData metricData1 = new MetricData();
        metricData1.setId(1L);
        MetricData metricData2 = new MetricData();
        metricData2.setId(metricData1.getId());
        assertThat(metricData1).isEqualTo(metricData2);
        metricData2.setId(2L);
        assertThat(metricData1).isNotEqualTo(metricData2);
        metricData1.setId(null);
        assertThat(metricData1).isNotEqualTo(metricData2);
    }
}
