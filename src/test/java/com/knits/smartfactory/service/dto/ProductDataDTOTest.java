package com.knits.smartfactory.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDataDTO.class);
        ProductDataDTO productDataDTO1 = new ProductDataDTO();
        productDataDTO1.setId(1L);
        ProductDataDTO productDataDTO2 = new ProductDataDTO();
        assertThat(productDataDTO1).isNotEqualTo(productDataDTO2);
        productDataDTO2.setId(productDataDTO1.getId());
        assertThat(productDataDTO1).isEqualTo(productDataDTO2);
        productDataDTO2.setId(2L);
        assertThat(productDataDTO1).isNotEqualTo(productDataDTO2);
        productDataDTO1.setId(null);
        assertThat(productDataDTO1).isNotEqualTo(productDataDTO2);
    }
}
