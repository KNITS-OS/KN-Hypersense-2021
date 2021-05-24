package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.ProductionLine;
import com.knits.smartfactory.repository.ProductionLineRepository;
import com.knits.smartfactory.service.ProductionLineService;
import com.knits.smartfactory.service.dto.ProductionLineDTO;
import com.knits.smartfactory.service.mapper.ProductionLineMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductionLine}.
 */
@Service
@Transactional
public class ProductionLineServiceImpl implements ProductionLineService {

    private final Logger log = LoggerFactory.getLogger(ProductionLineServiceImpl.class);

    private final ProductionLineRepository productionLineRepository;

    private final ProductionLineMapper productionLineMapper;

    public ProductionLineServiceImpl(ProductionLineRepository productionLineRepository, ProductionLineMapper productionLineMapper) {
        this.productionLineRepository = productionLineRepository;
        this.productionLineMapper = productionLineMapper;
    }

    @Override
    public ProductionLineDTO save(ProductionLineDTO productionLineDTO) {
        log.debug("Request to save ProductionLine : {}", productionLineDTO);
        ProductionLine productionLine = productionLineMapper.toEntity(productionLineDTO);
        productionLine = productionLineRepository.save(productionLine);
        return productionLineMapper.toDto(productionLine);
    }

    @Override
    public Optional<ProductionLineDTO> partialUpdate(ProductionLineDTO productionLineDTO) {
        log.debug("Request to partially update ProductionLine : {}", productionLineDTO);

        return productionLineRepository
            .findById(productionLineDTO.getId())
            .map(
                existingProductionLine -> {
                    productionLineMapper.partialUpdate(existingProductionLine, productionLineDTO);
                    return existingProductionLine;
                }
            )
            .map(productionLineRepository::save)
            .map(productionLineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionLineDTO> findAll() {
        log.debug("Request to get all ProductionLines");
        return productionLineRepository
            .findAll()
            .stream()
            .map(productionLineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductionLineDTO> findOne(Long id) {
        log.debug("Request to get ProductionLine : {}", id);
        return productionLineRepository.findById(id).map(productionLineMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductionLine : {}", id);
        productionLineRepository.deleteById(id);
    }
}
