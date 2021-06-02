package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity of the product that being produced.\n@author Vassili Moskaljov.\n@version 1.0
 */
@Entity
@Table(name = "product_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "scraped_qty")
    private Integer scrapedQty;

    @Column(name = "pending_qty")
    private Integer pendingQty;

    @Column(name = "rejected_qty")
    private Integer rejectedQty;

    @Column(name = "completed_qty")
    private Integer completedQty;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productData" }, allowSetters = true)
    private ProductionPlan productionPlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductData id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScrapedQty() {
        return this.scrapedQty;
    }

    public ProductData scrapedQty(Integer scrapedQty) {
        this.scrapedQty = scrapedQty;
        return this;
    }

    public void setScrapedQty(Integer scrapedQty) {
        this.scrapedQty = scrapedQty;
    }

    public Integer getPendingQty() {
        return this.pendingQty;
    }

    public ProductData pendingQty(Integer pendingQty) {
        this.pendingQty = pendingQty;
        return this;
    }

    public void setPendingQty(Integer pendingQty) {
        this.pendingQty = pendingQty;
    }

    public Integer getRejectedQty() {
        return this.rejectedQty;
    }

    public ProductData rejectedQty(Integer rejectedQty) {
        this.rejectedQty = rejectedQty;
        return this;
    }

    public void setRejectedQty(Integer rejectedQty) {
        this.rejectedQty = rejectedQty;
    }

    public Integer getCompletedQty() {
        return this.completedQty;
    }

    public ProductData completedQty(Integer completedQty) {
        this.completedQty = completedQty;
        return this;
    }

    public void setCompletedQty(Integer completedQty) {
        this.completedQty = completedQty;
    }

    public ProductionPlan getProductionPlan() {
        return this.productionPlan;
    }

    public ProductData productionPlan(ProductionPlan productionPlan) {
        this.setProductionPlan(productionPlan);
        return this;
    }

    public void setProductionPlan(ProductionPlan productionPlan) {
        this.productionPlan = productionPlan;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductData)) {
            return false;
        }
        return id != null && id.equals(((ProductData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", scrapedQty=" + getScrapedQty() +
            ", pendingQty=" + getPendingQty() +
            ", rejectedQty=" + getRejectedQty() +
            ", completedQty=" + getCompletedQty() +
            "}";
    }
}
