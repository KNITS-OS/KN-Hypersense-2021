package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.FactoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.Factory}.
 */
public interface FactoryService {
    /**
     * Save a factory.
     *
     * @param factoryDTO the entity to save.
     * @return the persisted entity.
     */
    FactoryDTO save(FactoryDTO factoryDTO);

    /**
     * Partially updates a factory.
     *
     * @param factoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FactoryDTO> partialUpdate(FactoryDTO factoryDTO);

    /**
     * Get all the factories.
     *
     * @return the list of entities.
     */
    List<FactoryDTO> findAll();

    /**
     * Get the "id" factory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FactoryDTO> findOne(Long id);

    /**
     * Delete the "id" factory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
