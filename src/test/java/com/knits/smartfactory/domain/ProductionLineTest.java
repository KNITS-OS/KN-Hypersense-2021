package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionLineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionLine.class);
        ProductionLine productionLine1 = new ProductionLine();
        productionLine1.setId(1L);
        ProductionLine productionLine2 = new ProductionLine();
        productionLine2.setId(productionLine1.getId());
        assertThat(productionLine1).isEqualTo(productionLine2);
        productionLine2.setId(2L);
        assertThat(productionLine1).isNotEqualTo(productionLine2);
        productionLine1.setId(null);
        assertThat(productionLine1).isNotEqualTo(productionLine2);
    }
}
