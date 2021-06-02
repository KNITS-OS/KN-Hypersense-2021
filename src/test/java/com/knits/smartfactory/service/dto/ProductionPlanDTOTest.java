package com.knits.smartfactory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionPlanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionPlanDTO.class);
        ProductionPlanDTO productionPlanDTO1 = new ProductionPlanDTO();
        productionPlanDTO1.setId(1L);
        ProductionPlanDTO productionPlanDTO2 = new ProductionPlanDTO();
        assertThat(productionPlanDTO1).isNotEqualTo(productionPlanDTO2);
        productionPlanDTO2.setId(productionPlanDTO1.getId());
        assertThat(productionPlanDTO1).isEqualTo(productionPlanDTO2);
        productionPlanDTO2.setId(2L);
        assertThat(productionPlanDTO1).isNotEqualTo(productionPlanDTO2);
        productionPlanDTO1.setId(null);
        assertThat(productionPlanDTO1).isNotEqualTo(productionPlanDTO2);
    }
}
