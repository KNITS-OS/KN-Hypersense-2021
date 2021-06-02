package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.MetricDataDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.MetricData}.
 */
public interface MetricDataService {
    /**
     * Save a metricData.
     *
     * @param metricDataDTO the entity to save.
     * @return the persisted entity.
     */
    MetricDataDTO save(MetricDataDTO metricDataDTO);

    /**
     * Partially updates a metricData.
     *
     * @param metricDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MetricDataDTO> partialUpdate(MetricDataDTO metricDataDTO);

    /**
     * Get all the metricData.
     *
     * @return the list of entities.
     */
    List<MetricDataDTO> findAll();

    /**
     * Get the "id" metricData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MetricDataDTO> findOne(Long id);

    /**
     * Delete the "id" metricData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
