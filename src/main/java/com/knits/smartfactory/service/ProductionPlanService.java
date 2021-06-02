package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.ProductionPlanDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.ProductionPlan}.
 */
public interface ProductionPlanService {
    /**
     * Save a productionPlan.
     *
     * @param productionPlanDTO the entity to save.
     * @return the persisted entity.
     */
    ProductionPlanDTO save(ProductionPlanDTO productionPlanDTO);

    /**
     * Partially updates a productionPlan.
     *
     * @param productionPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductionPlanDTO> partialUpdate(ProductionPlanDTO productionPlanDTO);

    /**
     * Get all the productionPlans.
     *
     * @return the list of entities.
     */
    List<ProductionPlanDTO> findAll();

    /**
     * Get the "id" productionPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductionPlanDTO> findOne(Long id);

    /**
     * Delete the "id" productionPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
