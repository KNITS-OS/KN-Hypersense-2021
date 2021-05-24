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
    @JsonIgnoreProperties(value = { "metric" }, allowSetters = true)
    private Set<MetricData> metricData = new HashSet<>();

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

    public Set<MetricData> getMetricData() {
        return this.metricData;
    }

    public Metric metricData(Set<MetricData> metricData) {
        this.setMetricData(metricData);
        return this;
    }

    public Metric addMetricData(MetricData metricData) {
        this.metricData.add(metricData);
        metricData.setMetric(this);
        return this;
    }

    public Metric removeMetricData(MetricData metricData) {
        this.metricData.remove(metricData);
        metricData.setMetric(null);
        return this;
    }

    public void setMetricData(Set<MetricData> metricData) {
        if (this.metricData != null) {
            this.metricData.forEach(i -> i.setMetric(null));
        }
        if (metricData != null) {
            metricData.forEach(i -> i.setMetric(this));
        }
        this.metricData = metricData;
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
