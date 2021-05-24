package com.knits.smartfactory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThingsDTO.class);
        ThingsDTO thingsDTO1 = new ThingsDTO();
        thingsDTO1.setId(1L);
        ThingsDTO thingsDTO2 = new ThingsDTO();
        assertThat(thingsDTO1).isNotEqualTo(thingsDTO2);
        thingsDTO2.setId(thingsDTO1.getId());
        assertThat(thingsDTO1).isEqualTo(thingsDTO2);
        thingsDTO2.setId(2L);
        assertThat(thingsDTO1).isNotEqualTo(thingsDTO2);
        thingsDTO1.setId(null);
        assertThat(thingsDTO1).isNotEqualTo(thingsDTO2);
    }
}
