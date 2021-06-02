package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.Metric} entity.
 */
@ApiModel(description = "Entity that holds up defined metrics.\n@author Vassili Moskaljov.\n@version 1.0")
public class MetricDTO implements Serializable {

    private Long id;

    private String thingUuid;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetricDTO)) {
            return false;
        }

        MetricDTO metricDTO = (MetricDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metricDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetricDTO{" +
            "id=" + getId() +
            ", thingUuid='" + getThingUuid() + "'" +
            "}";
    }
}
