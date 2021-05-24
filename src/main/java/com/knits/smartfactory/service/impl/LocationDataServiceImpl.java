package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.LocationData;
import com.knits.smartfactory.repository.LocationDataRepository;
import com.knits.smartfactory.service.LocationDataService;
import com.knits.smartfactory.service.dto.LocationDataDTO;
import com.knits.smartfactory.service.mapper.LocationDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LocationData}.
 */
@Service
@Transactional
public class LocationDataServiceImpl implements LocationDataService {

    private final Logger log = LoggerFactory.getLogger(LocationDataServiceImpl.class);

    private final LocationDataRepository locationDataRepository;

    private final LocationDataMapper locationDataMapper;

    public LocationDataServiceImpl(LocationDataRepository locationDataRepository, LocationDataMapper locationDataMapper) {
        this.locationDataRepository = locationDataRepository;
        this.locationDataMapper = locationDataMapper;
    }

    @Override
    public LocationDataDTO save(LocationDataDTO locationDataDTO) {
        log.debug("Request to save LocationData : {}", locationDataDTO);
        LocationData locationData = locationDataMapper.toEntity(locationDataDTO);
        locationData = locationDataRepository.save(locationData);
        return locationDataMapper.toDto(locationData);
    }

    @Override
    public Optional<LocationDataDTO> partialUpdate(LocationDataDTO locationDataDTO) {
        log.debug("Request to partially update LocationData : {}", locationDataDTO);

        return locationDataRepository
            .findById(locationDataDTO.getId())
            .map(
                existingLocationData -> {
                    locationDataMapper.partialUpdate(existingLocationData, locationDataDTO);
                    return existingLocationData;
                }
            )
            .map(locationDataRepository::save)
            .map(locationDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationDataDTO> findAll() {
        log.debug("Request to get all LocationData");
        return locationDataRepository.findAll().stream().map(locationDataMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocationDataDTO> findOne(Long id) {
        log.debug("Request to get LocationData : {}", id);
        return locationDataRepository.findById(id).map(locationDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LocationData : {}", id);
        locationDataRepository.deleteById(id);
    }
}
