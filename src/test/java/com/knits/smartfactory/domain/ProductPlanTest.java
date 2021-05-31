package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductPlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPlan.class);
        ProductPlan productPlan1 = new ProductPlan();
        productPlan1.setId(1L);
        ProductPlan productPlan2 = new ProductPlan();
        productPlan2.setId(productPlan1.getId());
        assertThat(productPlan1).isEqualTo(productPlan2);
        productPlan2.setId(2L);
        assertThat(productPlan1).isNotEqualTo(productPlan2);
        productPlan1.setId(null);
        assertThat(productPlan1).isNotEqualTo(productPlan2);
    }
}
