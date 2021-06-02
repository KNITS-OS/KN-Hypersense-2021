package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductionPlan.
 */
@Entity
@Table(name = "production_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "productionPlan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productionPlan" }, allowSetters = true)
    private Set<ProductData> productData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductionPlan id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public ProductionPlan dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getQty() {
        return this.qty;
    }

    public ProductionPlan qty(Integer qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getName() {
        return this.name;
    }

    public ProductionPlan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProductData> getProductData() {
        return this.productData;
    }

    public ProductionPlan productData(Set<ProductData> productData) {
        this.setProductData(productData);
        return this;
    }

    public ProductionPlan addProductData(ProductData productData) {
        this.productData.add(productData);
        productData.setProductionPlan(this);
        return this;
    }

    public ProductionPlan removeProductData(ProductData productData) {
        this.productData.remove(productData);
        productData.setProductionPlan(null);
        return this;
    }

    public void setProductData(Set<ProductData> productData) {
        if (this.productData != null) {
            this.productData.forEach(i -> i.setProductionPlan(null));
        }
        if (productData != null) {
            productData.forEach(i -> i.setProductionPlan(this));
        }
        this.productData = productData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionPlan)) {
            return false;
        }
        return id != null && id.equals(((ProductionPlan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionPlan{" +
            "id=" + getId() +
            ", dueDate='" + getDueDate() + "'" +
            ", qty=" + getQty() +
            ", name='" + getName() + "'" +
            "}";
    }
}
