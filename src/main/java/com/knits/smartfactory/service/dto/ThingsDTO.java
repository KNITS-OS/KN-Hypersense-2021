package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.Things} entity.
 */
@ApiModel(description = "Entity that hold collection of related Things from Core platform.\n@author Vassili Moskaljov.\n@version 1.0")
public class ThingsDTO implements Serializable {

    private Long id;

    private String thingUuid;

    private ProductionLineDTO productionLine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThingUuid() {
        return thingUuid;
    }

    public void setThingUuid(String thingUuid) {
        this.thingUuid = thingUuid;
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
        if (!(o instanceof ThingsDTO)) {
            return false;
        }

        ThingsDTO thingsDTO = (ThingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, thingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThingsDTO{" +
            "id=" + getId() +
            ", thingUuid='" + getThingUuid() + "'" +
            ", productionLine=" + getProductionLine() +
            "}";
    }
}
