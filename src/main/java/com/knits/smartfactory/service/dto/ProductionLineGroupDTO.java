package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.ProductionLineGroup} entity.
 */
@ApiModel(description = "Entity that groups up Production lines.\n@author Vassili Moskaljov.\n@version 1.0")
public class ProductionLineGroupDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private FactoryDTO factory;

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

    public FactoryDTO getFactory() {
        return factory;
    }

    public void setFactory(FactoryDTO factory) {
        this.factory = factory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionLineGroupDTO)) {
            return false;
        }

        ProductionLineGroupDTO productionLineGroupDTO = (ProductionLineGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productionLineGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionLineGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", factory=" + getFactory() +
            "}";
    }
}
