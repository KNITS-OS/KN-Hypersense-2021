package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.LocationDataDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.LocationData}.
 */
public interface LocationDataService {
    /**
     * Save a locationData.
     *
     * @param locationDataDTO the entity to save.
     * @return the persisted entity.
     */
    LocationDataDTO save(LocationDataDTO locationDataDTO);

    /**
     * Partially updates a locationData.
     *
     * @param locationDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocationDataDTO> partialUpdate(LocationDataDTO locationDataDTO);

    /**
     * Get all the locationData.
     *
     * @return the list of entities.
     */
    List<LocationDataDTO> findAll();

    /**
     * Get the "id" locationData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocationDataDTO> findOne(Long id);

    /**
     * Delete the "id" locationData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
