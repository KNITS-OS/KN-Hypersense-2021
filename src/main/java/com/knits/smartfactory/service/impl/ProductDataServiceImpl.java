package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.ProductData;
import com.knits.smartfactory.repository.ProductDataRepository;
import com.knits.smartfactory.service.ProductDataService;
import com.knits.smartfactory.service.dto.ProductDataDTO;
import com.knits.smartfactory.service.mapper.ProductDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductData}.
 */
@Service
@Transactional
public class ProductDataServiceImpl implements ProductDataService {

    private final Logger log = LoggerFactory.getLogger(ProductDataServiceImpl.class);

    private final ProductDataRepository productDataRepository;

    private final ProductDataMapper productDataMapper;

    public ProductDataServiceImpl(ProductDataRepository productDataRepository, ProductDataMapper productDataMapper) {
        this.productDataRepository = productDataRepository;
        this.productDataMapper = productDataMapper;
    }

    @Override
    public ProductDataDTO save(ProductDataDTO productDataDTO) {
        log.debug("Request to save ProductData : {}", productDataDTO);
        ProductData productData = productDataMapper.toEntity(productDataDTO);
        productData = productDataRepository.save(productData);
        return productDataMapper.toDto(productData);
    }

    @Override
    public Optional<ProductDataDTO> partialUpdate(ProductDataDTO productDataDTO) {
        log.debug("Request to partially update ProductData : {}", productDataDTO);

        return productDataRepository
            .findById(productDataDTO.getId())
            .map(
                existingProductData -> {
                    productDataMapper.partialUpdate(existingProductData, productDataDTO);
                    return existingProductData;
                }
            )
            .map(productDataRepository::save)
            .map(productDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDataDTO> findAll() {
        log.debug("Request to get all ProductData");
        return productDataRepository.findAll().stream().map(productDataMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDataDTO> findOne(Long id) {
        log.debug("Request to get ProductData : {}", id);
        return productDataRepository.findById(id).map(productDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductData : {}", id);
        productDataRepository.deleteById(id);
    }
}
