package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.ProductionPlan;
import com.knits.smartfactory.repository.ProductionPlanRepository;
import com.knits.smartfactory.service.ProductionPlanService;
import com.knits.smartfactory.service.dto.ProductionPlanDTO;
import com.knits.smartfactory.service.mapper.ProductionPlanMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductionPlan}.
 */
@Service
@Transactional
public class ProductionPlanServiceImpl implements ProductionPlanService {

    private final Logger log = LoggerFactory.getLogger(ProductionPlanServiceImpl.class);

    private final ProductionPlanRepository productionPlanRepository;

    private final ProductionPlanMapper productionPlanMapper;

    public ProductionPlanServiceImpl(ProductionPlanRepository productionPlanRepository, ProductionPlanMapper productionPlanMapper) {
        this.productionPlanRepository = productionPlanRepository;
        this.productionPlanMapper = productionPlanMapper;
    }

    @Override
    public ProductionPlanDTO save(ProductionPlanDTO productionPlanDTO) {
        log.debug("Request to save ProductionPlan : {}", productionPlanDTO);
        ProductionPlan productionPlan = productionPlanMapper.toEntity(productionPlanDTO);
        productionPlan = productionPlanRepository.save(productionPlan);
        return productionPlanMapper.toDto(productionPlan);
    }

    @Override
    public Optional<ProductionPlanDTO> partialUpdate(ProductionPlanDTO productionPlanDTO) {
        log.debug("Request to partially update ProductionPlan : {}", productionPlanDTO);

        return productionPlanRepository
            .findById(productionPlanDTO.getId())
            .map(
                existingProductionPlan -> {
                    productionPlanMapper.partialUpdate(existingProductionPlan, productionPlanDTO);
                    return existingProductionPlan;
                }
            )
            .map(productionPlanRepository::save)
            .map(productionPlanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionPlanDTO> findAll() {
        log.debug("Request to get all ProductionPlans");
        return productionPlanRepository
            .findAll()
            .stream()
            .map(productionPlanMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductionPlanDTO> findOne(Long id) {
        log.debug("Request to get ProductionPlan : {}", id);
        return productionPlanRepository.findById(id).map(productionPlanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductionPlan : {}", id);
        productionPlanRepository.deleteById(id);
    }
}
