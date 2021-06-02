package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.CompanyUserDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.CompanyUser}.
 */
public interface CompanyUserService {
    /**
     * Save a companyUser.
     *
     * @param companyUserDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyUserDTO save(CompanyUserDTO companyUserDTO);

    /**
     * Partially updates a companyUser.
     *
     * @param companyUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyUserDTO> partialUpdate(CompanyUserDTO companyUserDTO);

    /**
     * Get all the companyUsers.
     *
     * @return the list of entities.
     */
    List<CompanyUserDTO> findAll();

    /**
     * Get the "id" companyUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyUserDTO> findOne(Long id);

    /**
     * Delete the "id" companyUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
