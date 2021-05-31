package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.ProductPlan;
import com.knits.smartfactory.repository.ProductPlanRepository;
import com.knits.smartfactory.service.ProductPlanService;
import com.knits.smartfactory.service.dto.ProductPlanDTO;
import com.knits.smartfactory.service.mapper.ProductPlanMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductPlan}.
 */
@Service
@Transactional
public class ProductPlanServiceImpl implements ProductPlanService {

    private final Logger log = LoggerFactory.getLogger(ProductPlanServiceImpl.class);

    private final ProductPlanRepository productPlanRepository;

    private final ProductPlanMapper productPlanMapper;

    public ProductPlanServiceImpl(ProductPlanRepository productPlanRepository, ProductPlanMapper productPlanMapper) {
        this.productPlanRepository = productPlanRepository;
        this.productPlanMapper = productPlanMapper;
    }

    @Override
    public ProductPlanDTO save(ProductPlanDTO productPlanDTO) {
        log.debug("Request to save ProductPlan : {}", productPlanDTO);
        ProductPlan productPlan = productPlanMapper.toEntity(productPlanDTO);
        productPlan = productPlanRepository.save(productPlan);
        return productPlanMapper.toDto(productPlan);
    }

    @Override
    public Optional<ProductPlanDTO> partialUpdate(ProductPlanDTO productPlanDTO) {
        log.debug("Request to partially update ProductPlan : {}", productPlanDTO);

        return productPlanRepository
            .findById(productPlanDTO.getId())
            .map(
                existingProductPlan -> {
                    productPlanMapper.partialUpdate(existingProductPlan, productPlanDTO);
                    return existingProductPlan;
                }
            )
            .map(productPlanRepository::save)
            .map(productPlanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductPlanDTO> findAll() {
        log.debug("Request to get all ProductPlans");
        return productPlanRepository.findAll().stream().map(productPlanMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductPlanDTO> findOne(Long id) {
        log.debug("Request to get ProductPlan : {}", id);
        return productPlanRepository.findById(id).map(productPlanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductPlan : {}", id);
        productPlanRepository.deleteById(id);
    }
}
