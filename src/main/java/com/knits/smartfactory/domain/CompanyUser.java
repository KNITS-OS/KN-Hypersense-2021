package com.knits.smartfactory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity that holds collection of related users.\n@author Vassili Moskaljov\n@version 1.0
 */
@Entity
@Table(name = "company_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "users_uuid")
    private String usersUuid;

    @ManyToOne
    @JsonIgnoreProperties(value = { "users", "factory" }, allowSetters = true)
    private BusinessUnit businessUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyUser id(Long id) {
        this.id = id;
        return this;
    }

    public String getUsersUuid() {
        return this.usersUuid;
    }

    public CompanyUser usersUuid(String usersUuid) {
        this.usersUuid = usersUuid;
        return this;
    }

    public void setUsersUuid(String usersUuid) {
        this.usersUuid = usersUuid;
    }

    public BusinessUnit getBusinessUnit() {
        return this.businessUnit;
    }

    public CompanyUser businessUnit(BusinessUnit businessUnit) {
        this.setBusinessUnit(businessUnit);
        return this;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyUser)) {
            return false;
        }
        return id != null && id.equals(((CompanyUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyUser{" +
            "id=" + getId() +
            ", usersUuid='" + getUsersUuid() + "'" +
            "}";
    }
}
