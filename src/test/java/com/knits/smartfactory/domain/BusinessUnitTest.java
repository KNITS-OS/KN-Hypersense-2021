package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessUnit.class);
        BusinessUnit businessUnit1 = new BusinessUnit();
        businessUnit1.setId(1L);
        BusinessUnit businessUnit2 = new BusinessUnit();
        businessUnit2.setId(businessUnit1.getId());
        assertThat(businessUnit1).isEqualTo(businessUnit2);
        businessUnit2.setId(2L);
        assertThat(businessUnit1).isNotEqualTo(businessUnit2);
        businessUnit1.setId(null);
        assertThat(businessUnit1).isNotEqualTo(businessUnit2);
    }
}
