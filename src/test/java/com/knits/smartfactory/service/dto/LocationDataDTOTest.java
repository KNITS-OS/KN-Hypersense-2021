package com.knits.smartfactory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationDataDTO.class);
        LocationDataDTO locationDataDTO1 = new LocationDataDTO();
        locationDataDTO1.setId(1L);
        LocationDataDTO locationDataDTO2 = new LocationDataDTO();
        assertThat(locationDataDTO1).isNotEqualTo(locationDataDTO2);
        locationDataDTO2.setId(locationDataDTO1.getId());
        assertThat(locationDataDTO1).isEqualTo(locationDataDTO2);
        locationDataDTO2.setId(2L);
        assertThat(locationDataDTO1).isNotEqualTo(locationDataDTO2);
        locationDataDTO1.setId(null);
        assertThat(locationDataDTO1).isNotEqualTo(locationDataDTO2);
    }
}
