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

    private String name;

    private Integer scrapedQty;

    private Integer pendingQty;

    private Integer rejectedQty;

    private Integer completedQty;

    private ProductionPlanDTO productionPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScrapedQty() {
        return scrapedQty;
    }

    public void setScrapedQty(Integer scrapedQty) {
        this.scrapedQty = scrapedQty;
    }

    public Integer getPendingQty() {
        return pendingQty;
    }

    public void setPendingQty(Integer pendingQty) {
        this.pendingQty = pendingQty;
    }

    public Integer getRejectedQty() {
        return rejectedQty;
    }

    public void setRejectedQty(Integer rejectedQty) {
        this.rejectedQty = rejectedQty;
    }

    public Integer getCompletedQty() {
        return completedQty;
    }

    public void setCompletedQty(Integer completedQty) {
        this.completedQty = completedQty;
    }

    public ProductionPlanDTO getProductionPlan() {
        return productionPlan;
    }

    public void setProductionPlan(ProductionPlanDTO productionPlan) {
        this.productionPlan = productionPlan;
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
            ", name='" + getName() + "'" +
            ", scrapedQty=" + getScrapedQty() +
            ", pendingQty=" + getPendingQty() +
            ", rejectedQty=" + getRejectedQty() +
            ", completedQty=" + getCompletedQty() +
            ", productionPlan=" + getProductionPlan() +
            "}";
    }
}
