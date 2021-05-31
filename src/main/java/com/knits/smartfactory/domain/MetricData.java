package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Set of specific metrics that measure activities.\n@author Vassili Moskaljov.\n@version 1.0
 */
@Entity
@Table(name = "metric_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MetricData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "time_stamp")
    private LocalDate timeStamp;

    @Column(name = "measure_value")
    private String measureValue;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "metricData" }, allowSetters = true)
    private Metric metric;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetricData id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getTimeStamp() {
        return this.timeStamp;
    }

    public MetricData timeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMeasureValue() {
        return this.measureValue;
    }

    public MetricData measureValue(String measureValue) {
        this.measureValue = measureValue;
        return this;
    }

    public void setMeasureValue(String measureValue) {
        this.measureValue = measureValue;
    }

    public String getName() {
        return this.name;
    }

    public MetricData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public MetricData status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Metric getMetric() {
        return this.metric;
    }

    public MetricData metric(Metric metric) {
        this.setMetric(metric);
        return this;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetricData)) {
            return false;
        }
        return id != null && id.equals(((MetricData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetricData{" +
            "id=" + getId() +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", measureValue='" + getMeasureValue() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
