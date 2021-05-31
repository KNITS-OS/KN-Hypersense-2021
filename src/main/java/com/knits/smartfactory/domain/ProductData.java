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

    @Column(name = "completed_qty")
    private Long completedQty;

    @Column(name = "scraped_qty")
    private Long scrapedQty;

    @Column(name = "pending_qty")
    private Long pendingQty;

    @Column(name = "rejected_qty")
    private Long rejectedQty;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productData" }, allowSetters = true)
    private ProductPlan productPlan;

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

    public Long getCompletedQty() {
        return this.completedQty;
    }

    public ProductData completedQty(Long completedQty) {
        this.completedQty = completedQty;
        return this;
    }

    public void setCompletedQty(Long completedQty) {
        this.completedQty = completedQty;
    }

    public Long getScrapedQty() {
        return this.scrapedQty;
    }

    public ProductData scrapedQty(Long scrapedQty) {
        this.scrapedQty = scrapedQty;
        return this;
    }

    public void setScrapedQty(Long scrapedQty) {
        this.scrapedQty = scrapedQty;
    }

    public Long getPendingQty() {
        return this.pendingQty;
    }

    public ProductData pendingQty(Long pendingQty) {
        this.pendingQty = pendingQty;
        return this;
    }

    public void setPendingQty(Long pendingQty) {
        this.pendingQty = pendingQty;
    }

    public Long getRejectedQty() {
        return this.rejectedQty;
    }

    public ProductData rejectedQty(Long rejectedQty) {
        this.rejectedQty = rejectedQty;
        return this;
    }

    public void setRejectedQty(Long rejectedQty) {
        this.rejectedQty = rejectedQty;
    }

    public ProductPlan getProductPlan() {
        return this.productPlan;
    }

    public ProductData productPlan(ProductPlan productPlan) {
        this.setProductPlan(productPlan);
        return this;
    }

    public void setProductPlan(ProductPlan productPlan) {
        this.productPlan = productPlan;
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
            ", completedQty=" + getCompletedQty() +
            ", scrapedQty=" + getScrapedQty() +
            ", pendingQty=" + getPendingQty() +
            ", rejectedQty=" + getRejectedQty() +
            "}";
    }
}
