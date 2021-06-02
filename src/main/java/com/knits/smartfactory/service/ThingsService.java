package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.ThingsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.Things}.
 */
public interface ThingsService {
    /**
     * Save a things.
     *
     * @param thingsDTO the entity to save.
     * @return the persisted entity.
     */
    ThingsDTO save(ThingsDTO thingsDTO);

    /**
     * Partially updates a things.
     *
     * @param thingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThingsDTO> partialUpdate(ThingsDTO thingsDTO);

    /**
     * Get all the things.
     *
     * @return the list of entities.
     */
    List<ThingsDTO> findAll();

    /**
     * Get the "id" things.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThingsDTO> findOne(Long id);

    /**
     * Delete the "id" things.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
