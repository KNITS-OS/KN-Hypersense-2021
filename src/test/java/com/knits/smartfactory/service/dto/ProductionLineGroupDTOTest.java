package com.knits.smartfactory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionLineGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionLineGroupDTO.class);
        ProductionLineGroupDTO productionLineGroupDTO1 = new ProductionLineGroupDTO();
        productionLineGroupDTO1.setId(1L);
        ProductionLineGroupDTO productionLineGroupDTO2 = new ProductionLineGroupDTO();
        assertThat(productionLineGroupDTO1).isNotEqualTo(productionLineGroupDTO2);
        productionLineGroupDTO2.setId(productionLineGroupDTO1.getId());
        assertThat(productionLineGroupDTO1).isEqualTo(productionLineGroupDTO2);
        productionLineGroupDTO2.setId(2L);
        assertThat(productionLineGroupDTO1).isNotEqualTo(productionLineGroupDTO2);
        productionLineGroupDTO1.setId(null);
        assertThat(productionLineGroupDTO1).isNotEqualTo(productionLineGroupDTO2);
    }
}
