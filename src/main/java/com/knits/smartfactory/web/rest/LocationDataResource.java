package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.LocationDataRepository;
import com.knits.smartfactory.service.LocationDataService;
import com.knits.smartfactory.service.dto.LocationDataDTO;
import com.knits.smartfactory.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.knits.smartfactory.domain.LocationData}.
 */
@RestController
@RequestMapping("/api")
public class LocationDataResource {

    private final Logger log = LoggerFactory.getLogger(LocationDataResource.class);

    private static final String ENTITY_NAME = "locationData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationDataService locationDataService;

    private final LocationDataRepository locationDataRepository;

    public LocationDataResource(LocationDataService locationDataService, LocationDataRepository locationDataRepository) {
        this.locationDataService = locationDataService;
        this.locationDataRepository = locationDataRepository;
    }

    /**
     * {@code POST  /location-data} : Create a new locationData.
     *
     * @param locationDataDTO the locationDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationDataDTO, or with status {@code 400 (Bad Request)} if the locationData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-data")
    public ResponseEntity<LocationDataDTO> createLocationData(@RequestBody LocationDataDTO locationDataDTO) throws URISyntaxException {
        log.debug("REST request to save LocationData : {}", locationDataDTO);
        if (locationDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationDataDTO result = locationDataService.save(locationDataDTO);
        return ResponseEntity
            .created(new URI("/api/location-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-data/:id} : Updates an existing locationData.
     *
     * @param id the id of the locationDataDTO to save.
     * @param locationDataDTO the locationDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationDataDTO,
     * or with status {@code 400 (Bad Request)} if the locationDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-data/{id}")
    public ResponseEntity<LocationDataDTO> updateLocationData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LocationDataDTO locationDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LocationData : {}, {}", id, locationDataDTO);
        if (locationDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LocationDataDTO result = locationDataService.save(locationDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locationDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /location-data/:id} : Partial updates given fields of an existing locationData, field will ignore if it is null
     *
     * @param id the id of the locationDataDTO to save.
     * @param locationDataDTO the locationDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationDataDTO,
     * or with status {@code 400 (Bad Request)} if the locationDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the locationDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the locationDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/location-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LocationDataDTO> partialUpdateLocationData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LocationDataDTO locationDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LocationData partially : {}, {}", id, locationDataDTO);
        if (locationDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocationDataDTO> result = locationDataService.partialUpdate(locationDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locationDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /location-data} : get all the locationData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationData in body.
     */
    @GetMapping("/location-data")
    public List<LocationDataDTO> getAllLocationData() {
        log.debug("REST request to get all LocationData");
        return locationDataService.findAll();
    }

    /**
     * {@code GET  /location-data/:id} : get the "id" locationData.
     *
     * @param id the id of the locationDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-data/{id}")
    public ResponseEntity<LocationDataDTO> getLocationData(@PathVariable Long id) {
        log.debug("REST request to get LocationData : {}", id);
        Optional<LocationDataDTO> locationDataDTO = locationDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationDataDTO);
    }

    /**
     * {@code DELETE  /location-data/:id} : delete the "id" locationData.
     *
     * @param id the id of the locationDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-data/{id}")
    public ResponseEntity<Void> deleteLocationData(@PathVariable Long id) {
        log.debug("REST request to delete LocationData : {}", id);
        locationDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
