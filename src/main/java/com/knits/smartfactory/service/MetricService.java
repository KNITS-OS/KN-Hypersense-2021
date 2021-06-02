package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.MetricDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.Metric}.
 */
public interface MetricService {
    /**
     * Save a metric.
     *
     * @param metricDTO the entity to save.
     * @return the persisted entity.
     */
    MetricDTO save(MetricDTO metricDTO);

    /**
     * Partially updates a metric.
     *
     * @param metricDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MetricDTO> partialUpdate(MetricDTO metricDTO);

    /**
     * Get all the metrics.
     *
     * @return the list of entities.
     */
    List<MetricDTO> findAll();

    /**
     * Get the "id" metric.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MetricDTO> findOne(Long id);

    /**
     * Delete the "id" metric.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
