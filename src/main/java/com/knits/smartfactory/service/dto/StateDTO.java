package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.State} entity.
 */
@ApiModel(description = "Entity that holds current states of the Production line.\n@author Vassili Moskaljov.\n@version 1.0")
public class StateDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private ProductionLineDTO productionLine;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductionLineDTO getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(ProductionLineDTO productionLine) {
        this.productionLine = productionLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateDTO)) {
            return false;
        }

        StateDTO stateDTO = (StateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", productionLine=" + getProductionLine() +
            "}";
    }
}
