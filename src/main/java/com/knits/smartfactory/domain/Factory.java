package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Factory entity that represents current factory.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "factory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Factory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "factory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productionLines", "factory" }, allowSetters = true)
    private Set<ProductionLineGroup> productionLineGroups = new HashSet<>();

    @OneToMany(mappedBy = "factory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "factory" }, allowSetters = true)
    private Set<BusinessUnit> businessUnits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Factory id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Factory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Factory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return this.location;
    }

    public Factory location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return this.type;
    }

    public Factory type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<ProductionLineGroup> getProductionLineGroups() {
        return this.productionLineGroups;
    }

    public Factory productionLineGroups(Set<ProductionLineGroup> productionLineGroups) {
        this.setProductionLineGroups(productionLineGroups);
        return this;
    }

    public Factory addProductionLineGroup(ProductionLineGroup productionLineGroup) {
        this.productionLineGroups.add(productionLineGroup);
        productionLineGroup.setFactory(this);
        return this;
    }

    public Factory removeProductionLineGroup(ProductionLineGroup productionLineGroup) {
        this.productionLineGroups.remove(productionLineGroup);
        productionLineGroup.setFactory(null);
        return this;
    }

    public void setProductionLineGroups(Set<ProductionLineGroup> productionLineGroups) {
        if (this.productionLineGroups != null) {
            this.productionLineGroups.forEach(i -> i.setFactory(null));
        }
        if (productionLineGroups != null) {
            productionLineGroups.forEach(i -> i.setFactory(this));
        }
        this.productionLineGroups = productionLineGroups;
    }

    public Set<BusinessUnit> getBusinessUnits() {
        return this.businessUnits;
    }

    public Factory businessUnits(Set<BusinessUnit> businessUnits) {
        this.setBusinessUnits(businessUnits);
        return this;
    }

    public Factory addBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnits.add(businessUnit);
        businessUnit.setFactory(this);
        return this;
    }

    public Factory removeBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnits.remove(businessUnit);
        businessUnit.setFactory(null);
        return this;
    }

    public void setBusinessUnits(Set<BusinessUnit> businessUnits) {
        if (this.businessUnits != null) {
            this.businessUnits.forEach(i -> i.setFactory(null));
        }
        if (businessUnits != null) {
            businessUnits.forEach(i -> i.setFactory(this));
        }
        this.businessUnits = businessUnits;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factory)) {
            return false;
        }
        return id != null && id.equals(((Factory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Factory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", location='" + getLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
