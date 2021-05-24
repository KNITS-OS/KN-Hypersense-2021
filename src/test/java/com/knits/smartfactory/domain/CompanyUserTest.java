package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyUser.class);
        CompanyUser companyUser1 = new CompanyUser();
        companyUser1.setId(1L);
        CompanyUser companyUser2 = new CompanyUser();
        companyUser2.setId(companyUser1.getId());
        assertThat(companyUser1).isEqualTo(companyUser2);
        companyUser2.setId(2L);
        assertThat(companyUser1).isNotEqualTo(companyUser2);
        companyUser1.setId(null);
        assertThat(companyUser1).isNotEqualTo(companyUser2);
    }
}
