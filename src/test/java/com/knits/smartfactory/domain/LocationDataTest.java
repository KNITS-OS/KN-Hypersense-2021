package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationData.class);
        LocationData locationData1 = new LocationData();
        locationData1.setId(1L);
        LocationData locationData2 = new LocationData();
        locationData2.setId(locationData1.getId());
        assertThat(locationData1).isEqualTo(locationData2);
        locationData2.setId(2L);
        assertThat(locationData1).isNotEqualTo(locationData2);
        locationData1.setId(null);
        assertThat(locationData1).isNotEqualTo(locationData2);
    }
}
