package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.ProductionLineGroup;
import com.knits.smartfactory.repository.ProductionLineGroupRepository;
import com.knits.smartfactory.service.ProductionLineGroupService;
import com.knits.smartfactory.service.dto.ProductionLineGroupDTO;
import com.knits.smartfactory.service.mapper.ProductionLineGroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductionLineGroup}.
 */
@Service
@Transactional
public class ProductionLineGroupServiceImpl implements ProductionLineGroupService {

    private final Logger log = LoggerFactory.getLogger(ProductionLineGroupServiceImpl.class);

    private final ProductionLineGroupRepository productionLineGroupRepository;

    private final ProductionLineGroupMapper productionLineGroupMapper;

    public ProductionLineGroupServiceImpl(
        ProductionLineGroupRepository productionLineGroupRepository,
        ProductionLineGroupMapper productionLineGroupMapper
    ) {
        this.productionLineGroupRepository = productionLineGroupRepository;
        this.productionLineGroupMapper = productionLineGroupMapper;
    }

    @Override
    public ProductionLineGroupDTO save(ProductionLineGroupDTO productionLineGroupDTO) {
        log.debug("Request to save ProductionLineGroup : {}", productionLineGroupDTO);
        ProductionLineGroup productionLineGroup = productionLineGroupMapper.toEntity(productionLineGroupDTO);
        productionLineGroup = productionLineGroupRepository.save(productionLineGroup);
        return productionLineGroupMapper.toDto(productionLineGroup);
    }

    @Override
    public Optional<ProductionLineGroupDTO> partialUpdate(ProductionLineGroupDTO productionLineGroupDTO) {
        log.debug("Request to partially update ProductionLineGroup : {}", productionLineGroupDTO);

        return productionLineGroupRepository
            .findById(productionLineGroupDTO.getId())
            .map(
                existingProductionLineGroup -> {
                    productionLineGroupMapper.partialUpdate(existingProductionLineGroup, productionLineGroupDTO);
                    return existingProductionLineGroup;
                }
            )
            .map(productionLineGroupRepository::save)
            .map(productionLineGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionLineGroupDTO> findAll() {
        log.debug("Request to get all ProductionLineGroups");
        return productionLineGroupRepository
            .findAll()
            .stream()
            .map(productionLineGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductionLineGroupDTO> findOne(Long id) {
        log.debug("Request to get ProductionLineGroup : {}", id);
        return productionLineGroupRepository.findById(id).map(productionLineGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductionLineGroup : {}", id);
        productionLineGroupRepository.deleteById(id);
    }
}
