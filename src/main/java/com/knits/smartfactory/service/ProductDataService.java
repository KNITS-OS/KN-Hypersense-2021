package com.knits.smartfactory.service;

import com.knits.smartfactory.service.dto.ProductDataDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.smartfactory.domain.ProductData}.
 */
public interface ProductDataService {
    /**
     * Save a productData.
     *
     * @param productDataDTO the entity to save.
     * @return the persisted entity.
     */
    ProductDataDTO save(ProductDataDTO productDataDTO);

    /**
     * Partially updates a productData.
     *
     * @param productDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductDataDTO> partialUpdate(ProductDataDTO productDataDTO);

    /**
     * Get all the productData.
     *
     * @return the list of entities.
     */
    List<ProductDataDTO> findAll();

    /**
     * Get the "id" productData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductDataDTO> findOne(Long id);

    /**
     * Delete the "id" productData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
