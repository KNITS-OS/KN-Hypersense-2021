package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionPlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionPlan.class);
        ProductionPlan productionPlan1 = new ProductionPlan();
        productionPlan1.setId(1L);
        ProductionPlan productionPlan2 = new ProductionPlan();
        productionPlan2.setId(productionPlan1.getId());
        assertThat(productionPlan1).isEqualTo(productionPlan2);
        productionPlan2.setId(2L);
        assertThat(productionPlan1).isNotEqualTo(productionPlan2);
        productionPlan1.setId(null);
        assertThat(productionPlan1).isNotEqualTo(productionPlan2);
    }
}
