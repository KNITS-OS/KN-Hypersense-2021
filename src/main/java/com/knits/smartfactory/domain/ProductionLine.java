package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Enity of specific production line.\n@author Vassili Moskaljov.\n@version 1.0
 */
@Entity
@Table(name = "production_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private LocationData locationData;

    @OneToMany(mappedBy = "productionLine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productionLine" }, allowSetters = true)
    private Set<Things> things = new HashSet<>();

    @OneToMany(mappedBy = "productionLine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productionLine" }, allowSetters = true)
    private Set<State> states = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "productionLines", "factory" }, allowSetters = true)
    private ProductionLineGroup productionLineGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductionLine id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductionLine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductionLine description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationData getLocationData() {
        return this.locationData;
    }

    public ProductionLine locationData(LocationData locationData) {
        this.setLocationData(locationData);
        return this;
    }

    public void setLocationData(LocationData locationData) {
        this.locationData = locationData;
    }

    public Set<Things> getThings() {
        return this.things;
    }

    public ProductionLine things(Set<Things> things) {
        this.setThings(things);
        return this;
    }

    public ProductionLine addThings(Things things) {
        this.things.add(things);
        things.setProductionLine(this);
        return this;
    }

    public ProductionLine removeThings(Things things) {
        this.things.remove(things);
        things.setProductionLine(null);
        return this;
    }

    public void setThings(Set<Things> things) {
        if (this.things != null) {
            this.things.forEach(i -> i.setProductionLine(null));
        }
        if (things != null) {
            things.forEach(i -> i.setProductionLine(this));
        }
        this.things = things;
    }

    public Set<State> getStates() {
        return this.states;
    }

    public ProductionLine states(Set<State> states) {
        this.setStates(states);
        return this;
    }

    public ProductionLine addStates(State state) {
        this.states.add(state);
        state.setProductionLine(this);
        return this;
    }

    public ProductionLine removeStates(State state) {
        this.states.remove(state);
        state.setProductionLine(null);
        return this;
    }

    public void setStates(Set<State> states) {
        if (this.states != null) {
            this.states.forEach(i -> i.setProductionLine(null));
        }
        if (states != null) {
            states.forEach(i -> i.setProductionLine(this));
        }
        this.states = states;
    }

    public ProductionLineGroup getProductionLineGroup() {
        return this.productionLineGroup;
    }

    public ProductionLine productionLineGroup(ProductionLineGroup productionLineGroup) {
        this.setProductionLineGroup(productionLineGroup);
        return this;
    }

    public void setProductionLineGroup(ProductionLineGroup productionLineGroup) {
        this.productionLineGroup = productionLineGroup;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionLine)) {
            return false;
        }
        return id != null && id.equals(((ProductionLine) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionLine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
