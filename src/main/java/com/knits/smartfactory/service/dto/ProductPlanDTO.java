package com.knits.smartfactory.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.ProductPlan} entity.
 */
public class ProductPlanDTO implements Serializable {

    private Long id;

    private LocalDate estimatedTime;

    private Long qty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(LocalDate estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductPlanDTO)) {
            return false;
        }

        ProductPlanDTO productPlanDTO = (ProductPlanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productPlanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductPlanDTO{" +
            "id=" + getId() +
            ", estimatedTime='" + getEstimatedTime() + "'" +
            ", qty=" + getQty() +
            "}";
    }
}
