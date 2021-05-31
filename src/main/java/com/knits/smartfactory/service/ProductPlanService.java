package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.ProductPlanDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.ProductPlan}.
 */
public interface ProductPlanService {
    /**
     * Save a productPlan.
     *
     * @param productPlanDTO the entity to save.
     * @return the persisted entity.
     */
    ProductPlanDTO save(ProductPlanDTO productPlanDTO);

    /**
     * Partially updates a productPlan.
     *
     * @param productPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductPlanDTO> partialUpdate(ProductPlanDTO productPlanDTO);

    /**
     * Get all the productPlans.
     *
     * @return the list of entities.
     */
    List<ProductPlanDTO> findAll();

    /**
     * Get the "id" productPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductPlanDTO> findOne(Long id);

    /**
     * Delete the "id" productPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
