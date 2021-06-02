package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.ProductionLine} entity.
 */
@ApiModel(description = "Enity of specific production line.\n@author Vassili Moskaljov.\n@version 1.0")
public class ProductionLineDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private LocationDataDTO locationData;

    private ProductionLineGroupDTO productionLineGroup;

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

    public LocationDataDTO getLocationData() {
        return locationData;
    }

    public void setLocationData(LocationDataDTO locationData) {
        this.locationData = locationData;
    }

    public ProductionLineGroupDTO getProductionLineGroup() {
        return productionLineGroup;
    }

    public void setProductionLineGroup(ProductionLineGroupDTO productionLineGroup) {
        this.productionLineGroup = productionLineGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionLineDTO)) {
            return false;
        }

        ProductionLineDTO productionLineDTO = (ProductionLineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productionLineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionLineDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", locationData=" + getLocationData() +
            ", productionLineGroup=" + getProductionLineGroup() +
            "}";
    }
}
