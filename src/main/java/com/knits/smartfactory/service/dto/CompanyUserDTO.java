package com.knits.smartfactory.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.knits.smartfactory.domain.CompanyUser} entity.
 */
@ApiModel(description = "Entity that holds collection of related users.\n@author Vassili Moskaljov\n@version 1.0")
public class CompanyUserDTO implements Serializable {

    private Long id;

    private String usersUuid;

    private UserProfileDTO userProfile;

    private BusinessUnitDTO businessUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsersUuid() {
        return usersUuid;
    }

    public void setUsersUuid(String usersUuid) {
        this.usersUuid = usersUuid;
    }

    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    public BusinessUnitDTO getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnitDTO businessUnit) {
        this.businessUnit = businessUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyUserDTO)) {
            return false;
        }

        CompanyUserDTO companyUserDTO = (CompanyUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyUserDTO{" +
            "id=" + getId() +
            ", usersUuid='" + getUsersUuid() + "'" +
            ", userProfile=" + getUserProfile() +
            ", businessUnit=" + getBusinessUnit() +
            "}";
    }
}
