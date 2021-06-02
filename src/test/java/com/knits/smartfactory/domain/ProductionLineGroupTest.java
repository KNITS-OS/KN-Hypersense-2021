package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionLineGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionLineGroup.class);
        ProductionLineGroup productionLineGroup1 = new ProductionLineGroup();
        productionLineGroup1.setId(1L);
        ProductionLineGroup productionLineGroup2 = new ProductionLineGroup();
        productionLineGroup2.setId(productionLineGroup1.getId());
        assertThat(productionLineGroup1).isEqualTo(productionLineGroup2);
        productionLineGroup2.setId(2L);
        assertThat(productionLineGroup1).isNotEqualTo(productionLineGroup2);
        productionLineGroup1.setId(null);
        assertThat(productionLineGroup1).isNotEqualTo(productionLineGroup2);
    }
}
