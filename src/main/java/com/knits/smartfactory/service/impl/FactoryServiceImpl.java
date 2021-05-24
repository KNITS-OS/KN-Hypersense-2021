package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.Factory;
import com.knits.smartfactory.repository.FactoryRepository;
import com.knits.smartfactory.service.FactoryService;
import com.knits.smartfactory.service.dto.FactoryDTO;
import com.knits.smartfactory.service.mapper.FactoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Factory}.
 */
@Service
@Transactional
public class FactoryServiceImpl implements FactoryService {

    private final Logger log = LoggerFactory.getLogger(FactoryServiceImpl.class);

    private final FactoryRepository factoryRepository;

    private final FactoryMapper factoryMapper;

    public FactoryServiceImpl(FactoryRepository factoryRepository, FactoryMapper factoryMapper) {
        this.factoryRepository = factoryRepository;
        this.factoryMapper = factoryMapper;
    }

    @Override
    public FactoryDTO save(FactoryDTO factoryDTO) {
        log.debug("Request to save Factory : {}", factoryDTO);
        Factory factory = factoryMapper.toEntity(factoryDTO);
        factory = factoryRepository.save(factory);
        return factoryMapper.toDto(factory);
    }

    @Override
    public Optional<FactoryDTO> partialUpdate(FactoryDTO factoryDTO) {
        log.debug("Request to partially update Factory : {}", factoryDTO);

        return factoryRepository
            .findById(factoryDTO.getId())
            .map(
                existingFactory -> {
                    factoryMapper.partialUpdate(existingFactory, factoryDTO);
                    return existingFactory;
                }
            )
            .map(factoryRepository::save)
            .map(factoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FactoryDTO> findAll() {
        log.debug("Request to get all Factories");
        return factoryRepository.findAll().stream().map(factoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FactoryDTO> findOne(Long id) {
        log.debug("Request to get Factory : {}", id);
        return factoryRepository.findById(id).map(factoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Factory : {}", id);
        factoryRepository.deleteById(id);
    }
}
