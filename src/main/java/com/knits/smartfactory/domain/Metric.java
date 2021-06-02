package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity that holds up defined metrics.\n@author Vassili Moskaljov.\n@version 1.0
 */
@Entity
@Table(name = "metric")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Metric implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "thing_uuid")
    private String thingUuid;

    @OneToMany(mappedBy = "metric")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "status", "metric" }, allowSetters = true)
    private Set<MetricData> metrics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metric id(Long id) {
        this.id = id;
        return this;
    }

    public String getThingUuid() {
        return this.thingUuid;
    }

    public Metric thingUuid(String thingUuid) {
        this.thingUuid = thingUuid;
        return this;
    }

    public void setThingUuid(String thingUuid) {
        this.thingUuid = thingUuid;
    }

    public Set<MetricData> getMetrics() {
        return this.metrics;
    }

    public Metric metrics(Set<MetricData> metricData) {
        this.setMetrics(metricData);
        return this;
    }

    public Metric addMetrics(MetricData metricData) {
        this.metrics.add(metricData);
        metricData.setMetric(this);
        return this;
    }

    public Metric removeMetrics(MetricData metricData) {
        this.metrics.remove(metricData);
        metricData.setMetric(null);
        return this;
    }

    public void setMetrics(Set<MetricData> metricData) {
        if (this.metrics != null) {
            this.metrics.forEach(i -> i.setMetric(null));
        }
        if (metricData != null) {
            metricData.forEach(i -> i.setMetric(this));
        }
        this.metrics = metricData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metric)) {
            return false;
        }
        return id != null && id.equals(((Metric) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Metric{" +
            "id=" + getId() +
            ", thingUuid='" + getThingUuid() + "'" +
            "}";
    }
}
