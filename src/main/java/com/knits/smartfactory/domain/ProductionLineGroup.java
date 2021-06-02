package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity that groups up Production lines.\n@author Vassili Moskaljov.\n@version 1.0
 */
@Entity
@Table(name = "production_line_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionLineGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "productionLineGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "locationData", "things", "states", "productionLineGroup" }, allowSetters = true)
    private Set<ProductionLine> productionLines = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "productionLineGroups", "businessUnits" }, allowSetters = true)
    private Factory factory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductionLineGroup id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductionLineGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductionLineGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProductionLine> getProductionLines() {
        return this.productionLines;
    }

    public ProductionLineGroup productionLines(Set<ProductionLine> productionLines) {
        this.setProductionLines(productionLines);
        return this;
    }

    public ProductionLineGroup addProductionLine(ProductionLine productionLine) {
        this.productionLines.add(productionLine);
        productionLine.setProductionLineGroup(this);
        return this;
    }

    public ProductionLineGroup removeProductionLine(ProductionLine productionLine) {
        this.productionLines.remove(productionLine);
        productionLine.setProductionLineGroup(null);
        return this;
    }

    public void setProductionLines(Set<ProductionLine> productionLines) {
        if (this.productionLines != null) {
            this.productionLines.forEach(i -> i.setProductionLineGroup(null));
        }
        if (productionLines != null) {
            productionLines.forEach(i -> i.setProductionLineGroup(this));
        }
        this.productionLines = productionLines;
    }

    public Factory getFactory() {
        return this.factory;
    }

    public ProductionLineGroup factory(Factory factory) {
        this.setFactory(factory);
        return this;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionLineGroup)) {
            return false;
        }
        return id != null && id.equals(((ProductionLineGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionLineGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
