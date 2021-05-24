package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.LocationData} entity.
 */
@ApiModel(description = "Entity that hold location specific to Production line.\n@author Vassili Moskaljov.\n@version 1.0")
public class LocationDataDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationDataDTO)) {
            return false;
        }

        LocationDataDTO locationDataDTO = (LocationDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locationDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationDataDTO{" +
            "id=" + getId() +
            "}";
    }
}
