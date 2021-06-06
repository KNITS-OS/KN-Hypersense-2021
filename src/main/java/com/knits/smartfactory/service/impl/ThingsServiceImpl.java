package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.Things;
import com.knits.smartfactory.repository.ThingsRepository;
import com.knits.smartfactory.service.ThingsService;
import com.knits.smartfactory.service.dto.ThingsDTO;
import com.knits.smartfactory.service.mapper.ThingsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Things}.
 */
@Service
@Transactional
public class ThingsServiceImpl implements ThingsService {

    private final Logger log = LoggerFactory.getLogger(ThingsServiceImpl.class);

    private final ThingsRepository thingsRepository;

    private final ThingsMapper thingsMapper;

    public ThingsServiceImpl(ThingsRepository thingsRepository, ThingsMapper thingsMapper) {
        this.thingsRepository = thingsRepository;
        this.thingsMapper = thingsMapper;
    }

    @Override
    public ThingsDTO save(ThingsDTO thingsDTO) {
        log.debug("Request to save Things : {}", thingsDTO);
        Things things = thingsMapper.toEntity(thingsDTO);
        things = thingsRepository.save(things);
        return thingsMapper.toDto(things);
    }

    @Override
    public Optional<ThingsDTO> partialUpdate(ThingsDTO thingsDTO) {
        log.debug("Request to partially update Things : {}", thingsDTO);

        return thingsRepository
            .findById(thingsDTO.getId())
            .map(
                existingThings -> {
                    thingsMapper.partialUpdate(existingThings, thingsDTO);
                    return existingThings;
                }
            )
            .map(thingsRepository::save)
            .map(thingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThingsDTO> findAll() {
        log.debug("Request to get all Things");
        {
            Client client = ClientBuilder.newBuilder().build();
            WebTarget target = client.target("https://coreplatform.herokuapp.com:443/api/things");
            Response response = target.request().get();
            String value = response.readEntity(String.class);
            response.close(); // You should close connections!
        }

        return thingsRepository.findAll().stream().map(thingsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThingsDTO> findOne(Long id) {
        log.debug("Request to get Things : {}", id);
        return thingsRepository.findById(id).map(thingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Things : {}", id);
        thingsRepository.deleteById(id);
    }
}
