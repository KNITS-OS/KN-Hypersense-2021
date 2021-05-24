package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.MetricDataRepository;
import com.knits.smartfactory.service.MetricDataService;
import com.knits.smartfactory.service.dto.MetricDataDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.MetricData}.
 */
@RestController
@RequestMapping("/api")
public class MetricDataResource {

    private final Logger log = LoggerFactory.getLogger(MetricDataResource.class);

    private static final String ENTITY_NAME = "metricData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetricDataService metricDataService;

    private final MetricDataRepository metricDataRepository;

    public MetricDataResource(MetricDataService metricDataService, MetricDataRepository metricDataRepository) {
        this.metricDataService = metricDataService;
        this.metricDataRepository = metricDataRepository;
    }

    /**
     * {@code POST  /metric-data} : Create a new metricData.
     *
     * @param metricDataDTO the metricDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metricDataDTO, or with status {@code 400 (Bad Request)} if the metricData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/metric-data")
    public ResponseEntity<MetricDataDTO> createMetricData(@RequestBody MetricDataDTO metricDataDTO) throws URISyntaxException {
        log.debug("REST request to save MetricData : {}", metricDataDTO);
        if (metricDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new metricData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetricDataDTO result = metricDataService.save(metricDataDTO);
        return ResponseEntity
            .created(new URI("/api/metric-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /metric-data/:id} : Updates an existing metricData.
     *
     * @param id the id of the metricDataDTO to save.
     * @param metricDataDTO the metricDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metricDataDTO,
     * or with status {@code 400 (Bad Request)} if the metricDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metricDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/metric-data/{id}")
    public ResponseEntity<MetricDataDTO> updateMetricData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetricDataDTO metricDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MetricData : {}, {}", id, metricDataDTO);
        if (metricDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metricDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metricDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetricDataDTO result = metricDataService.save(metricDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metricDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /metric-data/:id} : Partial updates given fields of an existing metricData, field will ignore if it is null
     *
     * @param id the id of the metricDataDTO to save.
     * @param metricDataDTO the metricDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metricDataDTO,
     * or with status {@code 400 (Bad Request)} if the metricDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metricDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metricDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/metric-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MetricDataDTO> partialUpdateMetricData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetricDataDTO metricDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MetricData partially : {}, {}", id, metricDataDTO);
        if (metricDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metricDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metricDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetricDataDTO> result = metricDataService.partialUpdate(metricDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metricDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /metric-data} : get all the metricData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metricData in body.
     */
    @GetMapping("/metric-data")
    public List<MetricDataDTO> getAllMetricData() {
        log.debug("REST request to get all MetricData");
        return metricDataService.findAll();
    }

    /**
     * {@code GET  /metric-data/:id} : get the "id" metricData.
     *
     * @param id the id of the metricDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metricDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/metric-data/{id}")
    public ResponseEntity<MetricDataDTO> getMetricData(@PathVariable Long id) {
        log.debug("REST request to get MetricData : {}", id);
        Optional<MetricDataDTO> metricDataDTO = metricDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metricDataDTO);
    }

    /**
     * {@code DELETE  /metric-data/:id} : delete the "id" metricData.
     *
     * @param id the id of the metricDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/metric-data/{id}")
    public ResponseEntity<Void> deleteMetricData(@PathVariable Long id) {
        log.debug("REST request to delete MetricData : {}", id);
        metricDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
