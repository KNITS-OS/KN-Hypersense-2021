package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.ProductData} entity.
 */
@ApiModel(description = "Entity of the product that being produced.\n@author Vassili Moskaljov.\n@version 1.0")
public class ProductDataDTO implements Serializable {

    private Long id;

    private Long completedQty;

    private Long scrapedQty;

    private Long pendingQty;

    private Long rejectedQty;

    private ProductPlanDTO productPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompletedQty() {
        return completedQty;
    }

    public void setCompletedQty(Long completedQty) {
        this.completedQty = completedQty;
    }

    public Long getScrapedQty() {
        return scrapedQty;
    }

    public void setScrapedQty(Long scrapedQty) {
        this.scrapedQty = scrapedQty;
    }

    public Long getPendingQty() {
        return pendingQty;
    }

    public void setPendingQty(Long pendingQty) {
        this.pendingQty = pendingQty;
    }

    public Long getRejectedQty() {
        return rejectedQty;
    }

    public void setRejectedQty(Long rejectedQty) {
        this.rejectedQty = rejectedQty;
    }

    public ProductPlanDTO getProductPlan() {
        return productPlan;
    }

    public void setProductPlan(ProductPlanDTO productPlan) {
        this.productPlan = productPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDataDTO)) {
            return false;
        }

        ProductDataDTO productDataDTO = (ProductDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDataDTO{" +
            "id=" + getId() +
            ", completedQty=" + getCompletedQty() +
            ", scrapedQty=" + getScrapedQty() +
            ", pendingQty=" + getPendingQty() +
            ", rejectedQty=" + getRejectedQty() +
            ", productPlan=" + getProductPlan() +
            "}";
    }
}
