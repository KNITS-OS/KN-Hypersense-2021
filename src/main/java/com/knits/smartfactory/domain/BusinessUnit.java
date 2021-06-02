package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Representation of the specific group of users defined by organization.\n@author Vassili Moskaljov.\n@version 1.0
 */
@Entity
@Table(name = "business_unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BusinessUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "businessUnit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userProfile", "businessUnit" }, allowSetters = true)
    private Set<CompanyUser> users = new HashSet<>();

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

    public BusinessUnit id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public BusinessUnit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public BusinessUnit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CompanyUser> getUsers() {
        return this.users;
    }

    public BusinessUnit users(Set<CompanyUser> companyUsers) {
        this.setUsers(companyUsers);
        return this;
    }

    public BusinessUnit addUsers(CompanyUser companyUser) {
        this.users.add(companyUser);
        companyUser.setBusinessUnit(this);
        return this;
    }

    public BusinessUnit removeUsers(CompanyUser companyUser) {
        this.users.remove(companyUser);
        companyUser.setBusinessUnit(null);
        return this;
    }

    public void setUsers(Set<CompanyUser> companyUsers) {
        if (this.users != null) {
            this.users.forEach(i -> i.setBusinessUnit(null));
        }
        if (companyUsers != null) {
            companyUsers.forEach(i -> i.setBusinessUnit(this));
        }
        this.users = companyUsers;
    }

    public Factory getFactory() {
        return this.factory;
    }

    public BusinessUnit factory(Factory factory) {
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
        if (!(o instanceof BusinessUnit)) {
            return false;
        }
        return id != null && id.equals(((BusinessUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessUnit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
