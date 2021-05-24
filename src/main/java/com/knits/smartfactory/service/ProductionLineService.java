package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.ProductionLineDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.ProductionLine}.
 */
public interface ProductionLineService {
    /**
     * Save a productionLine.
     *
     * @param productionLineDTO the entity to save.
     * @return the persisted entity.
     */
    ProductionLineDTO save(ProductionLineDTO productionLineDTO);

    /**
     * Partially updates a productionLine.
     *
     * @param productionLineDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductionLineDTO> partialUpdate(ProductionLineDTO productionLineDTO);

    /**
     * Get all the productionLines.
     *
     * @return the list of entities.
     */
    List<ProductionLineDTO> findAll();

    /**
     * Get the "id" productionLine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductionLineDTO> findOne(Long id);

    /**
     * Delete the "id" productionLine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
