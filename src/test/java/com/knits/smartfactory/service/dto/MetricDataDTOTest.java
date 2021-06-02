package com.knits.smartfactory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MetricDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetricDataDTO.class);
        MetricDataDTO metricDataDTO1 = new MetricDataDTO();
        metricDataDTO1.setId(1L);
        MetricDataDTO metricDataDTO2 = new MetricDataDTO();
        assertThat(metricDataDTO1).isNotEqualTo(metricDataDTO2);
        metricDataDTO2.setId(metricDataDTO1.getId());
        assertThat(metricDataDTO1).isEqualTo(metricDataDTO2);
        metricDataDTO2.setId(2L);
        assertThat(metricDataDTO1).isNotEqualTo(metricDataDTO2);
        metricDataDTO1.setId(null);
        assertThat(metricDataDTO1).isNotEqualTo(metricDataDTO2);
    }
}
