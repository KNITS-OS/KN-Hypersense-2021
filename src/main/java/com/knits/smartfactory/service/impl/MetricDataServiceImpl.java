package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.MetricData;
import com.knits.smartfactory.repository.MetricDataRepository;
import com.knits.smartfactory.service.MetricDataService;
import com.knits.smartfactory.service.dto.MetricDataDTO;
import com.knits.smartfactory.service.mapper.MetricDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MetricData}.
 */
@Service
@Transactional
public class MetricDataServiceImpl implements MetricDataService {

    private final Logger log = LoggerFactory.getLogger(MetricDataServiceImpl.class);

    private final MetricDataRepository metricDataRepository;

    private final MetricDataMapper metricDataMapper;

    public MetricDataServiceImpl(MetricDataRepository metricDataRepository, MetricDataMapper metricDataMapper) {
        this.metricDataRepository = metricDataRepository;
        this.metricDataMapper = metricDataMapper;
    }

    @Override
    public MetricDataDTO save(MetricDataDTO metricDataDTO) {
        log.debug("Request to save MetricData : {}", metricDataDTO);
        MetricData metricData = metricDataMapper.toEntity(metricDataDTO);
        metricData = metricDataRepository.save(metricData);
        return metricDataMapper.toDto(metricData);
    }

    @Override
    public Optional<MetricDataDTO> partialUpdate(MetricDataDTO metricDataDTO) {
        log.debug("Request to partially update MetricData : {}", metricDataDTO);

        return metricDataRepository
            .findById(metricDataDTO.getId())
            .map(
                existingMetricData -> {
                    metricDataMapper.partialUpdate(existingMetricData, metricDataDTO);
                    return existingMetricData;
                }
            )
            .map(metricDataRepository::save)
            .map(metricDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetricDataDTO> findAll() {
        log.debug("Request to get all MetricData");
        return metricDataRepository.findAll().stream().map(metricDataMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MetricDataDTO> findOne(Long id) {
        log.debug("Request to get MetricData : {}", id);
        return metricDataRepository.findById(id).map(metricDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MetricData : {}", id);
        metricDataRepository.deleteById(id);
    }
}
