package com.knits.smartfactory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductPlanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPlanDTO.class);
        ProductPlanDTO productPlanDTO1 = new ProductPlanDTO();
        productPlanDTO1.setId(1L);
        ProductPlanDTO productPlanDTO2 = new ProductPlanDTO();
        assertThat(productPlanDTO1).isNotEqualTo(productPlanDTO2);
        productPlanDTO2.setId(productPlanDTO1.getId());
        assertThat(productPlanDTO1).isEqualTo(productPlanDTO2);
        productPlanDTO2.setId(2L);
        assertThat(productPlanDTO1).isNotEqualTo(productPlanDTO2);
        productPlanDTO1.setId(null);
        assertThat(productPlanDTO1).isNotEqualTo(productPlanDTO2);
    }
}
