package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.ProductionLineGroupDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.ProductionLineGroup}.
 */
public interface ProductionLineGroupService {
    /**
     * Save a productionLineGroup.
     *
     * @param productionLineGroupDTO the entity to save.
     * @return the persisted entity.
     */
    ProductionLineGroupDTO save(ProductionLineGroupDTO productionLineGroupDTO);

    /**
     * Partially updates a productionLineGroup.
     *
     * @param productionLineGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductionLineGroupDTO> partialUpdate(ProductionLineGroupDTO productionLineGroupDTO);

    /**
     * Get all the productionLineGroups.
     *
     * @return the list of entities.
     */
    List<ProductionLineGroupDTO> findAll();

    /**
     * Get the "id" productionLineGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductionLineGroupDTO> findOne(Long id);

    /**
     * Delete the "id" productionLineGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
