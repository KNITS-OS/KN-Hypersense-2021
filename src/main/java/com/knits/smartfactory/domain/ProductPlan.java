package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductPlan.
 */
@Entity
@Table(name = "product_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "estimated_time")
    private LocalDate estimatedTime;

    @Column(name = "qty")
    private Long qty;

    @OneToMany(mappedBy = "productPlan")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productPlan" }, allowSetters = true)
    private Set<ProductData> productData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductPlan id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getEstimatedTime() {
        return this.estimatedTime;
    }

    public ProductPlan estimatedTime(LocalDate estimatedTime) {
        this.estimatedTime = estimatedTime;
        return this;
    }

    public void setEstimatedTime(LocalDate estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Long getQty() {
        return this.qty;
    }

    public ProductPlan qty(Long qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Set<ProductData> getProductData() {
        return this.productData;
    }

    public ProductPlan productData(Set<ProductData> productData) {
        this.setProductData(productData);
        return this;
    }

    public ProductPlan addProductData(ProductData productData) {
        this.productData.add(productData);
        productData.setProductPlan(this);
        return this;
    }

    public ProductPlan removeProductData(ProductData productData) {
        this.productData.remove(productData);
        productData.setProductPlan(null);
        return this;
    }

    public void setProductData(Set<ProductData> productData) {
        if (this.productData != null) {
            this.productData.forEach(i -> i.setProductPlan(null));
        }
        if (productData != null) {
            productData.forEach(i -> i.setProductPlan(this));
        }
        this.productData = productData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductPlan)) {
            return false;
        }
        return id != null && id.equals(((ProductPlan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductPlan{" +
            "id=" + getId() +
            ", estimatedTime='" + getEstimatedTime() + "'" +
            ", qty=" + getQty() +
            "}";
    }
}
