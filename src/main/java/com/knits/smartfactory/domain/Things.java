package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity that hold collection of related Things from Core platform.\n@author Vassili Moskaljov.\n@version 1.0
 */
@Entity
@Table(name = "things")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Things implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "thing_uuid")
    private String thingUuid;

    @ManyToOne
    @JsonIgnoreProperties(value = { "locationData", "things", "states", "productionLineGroup" }, allowSetters = true)
    private ProductionLine productionLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Things id(Long id) {
        this.id = id;
        return this;
    }

    public String getThingUuid() {
        return this.thingUuid;
    }

    public Things thingUuid(String thingUuid) {
        this.thingUuid = thingUuid;
        return this;
    }

    public void setThingUuid(String thingUuid) {
        this.thingUuid = thingUuid;
    }

    public ProductionLine getProductionLine() {
        return this.productionLine;
    }

    public Things productionLine(ProductionLine productionLine) {
        this.setProductionLine(productionLine);
        return this;
    }

    public void setProductionLine(ProductionLine productionLine) {
        this.productionLine = productionLine;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Things)) {
            return false;
        }
        return id != null && id.equals(((Things) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Things{" +
            "id=" + getId() +
            ", thingUuid='" + getThingUuid() + "'" +
            "}";
    }
}
