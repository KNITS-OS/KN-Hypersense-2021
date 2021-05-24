package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Things.class);
        Things things1 = new Things();
        things1.setId(1L);
        Things things2 = new Things();
        things2.setId(things1.getId());
        assertThat(things1).isEqualTo(things2);
        things2.setId(2L);
        assertThat(things1).isNotEqualTo(things2);
        things1.setId(null);
        assertThat(things1).isNotEqualTo(things2);
    }
}
