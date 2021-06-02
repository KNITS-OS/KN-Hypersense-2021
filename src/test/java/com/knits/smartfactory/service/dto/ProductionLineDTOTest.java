package com.knits.smartfactory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionLineDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionLineDTO.class);
        ProductionLineDTO productionLineDTO1 = new ProductionLineDTO();
        productionLineDTO1.setId(1L);
        ProductionLineDTO productionLineDTO2 = new ProductionLineDTO();
        assertThat(productionLineDTO1).isNotEqualTo(productionLineDTO2);
        productionLineDTO2.setId(productionLineDTO1.getId());
        assertThat(productionLineDTO1).isEqualTo(productionLineDTO2);
        productionLineDTO2.setId(2L);
        assertThat(productionLineDTO1).isNotEqualTo(productionLineDTO2);
        productionLineDTO1.setId(null);
        assertThat(productionLineDTO1).isNotEqualTo(productionLineDTO2);
    }
}
