package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.MetricData} entity.
 */
@ApiModel(description = "Set of specific metrics that measure activities.\n@author Vassili Moskaljov.\n@version 1.0")
public class MetricDataDTO implements Serializable {

    private Long id;

    private Instant timeStamp;

    private String measureValue;

    private String name;

    private StatusDTO status;

    private MetricDTO metric;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMeasureValue() {
        return measureValue;
    }

    public void setMeasureValue(String measureValue) {
        this.measureValue = measureValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
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
            ", timeStamp='" + getTimeStamp() + "'" +
            ", measureValue='" + getMeasureValue() + "'" +
            ", name='" + getName() + "'" +
            ", status=" + getStatus() +
            ", metric=" + getMetric() +
            "}";
    }
}
