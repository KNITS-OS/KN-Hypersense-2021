package com.knits.smartfactory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessUnitDTO.class);
        BusinessUnitDTO businessUnitDTO1 = new BusinessUnitDTO();
        businessUnitDTO1.setId(1L);
        BusinessUnitDTO businessUnitDTO2 = new BusinessUnitDTO();
        assertThat(businessUnitDTO1).isNotEqualTo(businessUnitDTO2);
        businessUnitDTO2.setId(businessUnitDTO1.getId());
        assertThat(businessUnitDTO1).isEqualTo(businessUnitDTO2);
        businessUnitDTO2.setId(2L);
        assertThat(businessUnitDTO1).isNotEqualTo(businessUnitDTO2);
        businessUnitDTO1.setId(null);
        assertThat(businessUnitDTO1).isNotEqualTo(businessUnitDTO2);
    }
}
