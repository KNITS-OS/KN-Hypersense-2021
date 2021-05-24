package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.MetricData} entity.
 */
@ApiModel(description = "Set of specific metrics that measure activities.\n@author Vassili Moskaljov.\n@version 1.0")
public class MetricDataDTO implements Serializable {

    private Long id;

    private MetricDTO metric;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetricDTO getMetric() {
        return metric;
    }

    public void setMetric(MetricDTO metric) {
        this.metric = metric;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetricDataDTO)) {
            return false;
        }

        MetricDataDTO metricDataDTO = (MetricDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metricDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetricDataDTO{" +
            "id=" + getId() +
            ", metric=" + getMetric() +
            "}";
    }
}
