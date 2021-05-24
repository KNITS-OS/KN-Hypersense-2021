package com.knits.smartfactory.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.smartfactory.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductData.class);
        ProductData productData1 = new ProductData();
        productData1.setId(1L);
        ProductData productData2 = new ProductData();
        productData2.setId(productData1.getId());
        assertThat(productData1).isEqualTo(productData2);
        productData2.setId(2L);
        assertThat(productData1).isNotEqualTo(productData2);
        productData1.setId(null);
        assertThat(productData1).isNotEqualTo(productData2);
    }
}
