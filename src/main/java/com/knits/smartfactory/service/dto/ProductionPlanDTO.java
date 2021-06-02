package com.knits.smartfactory.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.ProductionPlan} entity.
 */
public class ProductionPlanDTO implements Serializable {

    private Long id;

    private Instant dueDate;

    private Integer qty;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionPlanDTO)) {
            return false;
        }

        ProductionPlanDTO productionPlanDTO = (ProductionPlanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productionPlanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionPlanDTO{" +
            "id=" + getId() +
            ", dueDate='" + getDueDate() + "'" +
            ", qty=" + getQty() +
            ", name='" + getName() + "'" +
            "}";
    }
}
