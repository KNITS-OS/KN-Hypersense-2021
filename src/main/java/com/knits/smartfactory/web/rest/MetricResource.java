package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.MetricRepository;
import com.knits.smartfactory.service.MetricService;
import com.knits.smartfactory.service.dto.MetricDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.Metric}.
 */
@RestController
@RequestMapping("/api")
public class MetricResource {

    private final Logger log = LoggerFactory.getLogger(MetricResource.class);

    private static final String ENTITY_NAME = "metric";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetricService metricService;

    private final MetricRepository metricRepository;

    public MetricResource(MetricService metricService, MetricRepository metricRepository) {
        this.metricService = metricService;
        this.metricRepository = metricRepository;
    }

    /**
     * {@code POST  /metrics} : Create a new metric.
     *
     * @param metricDTO the metricDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metricDTO, or with status {@code 400 (Bad Request)} if the metric has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/metrics")
    public ResponseEntity<MetricDTO> createMetric(@RequestBody MetricDTO metricDTO) throws URISyntaxException {
        log.debug("REST request to save Metric : {}", metricDTO);
        if (metricDTO.getId() != null) {
            throw new BadRequestAlertException("A new metric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetricDTO result = metricService.save(metricDTO);
        return ResponseEntity
            .created(new URI("/api/metrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /metrics/:id} : Updates an existing metric.
     *
     * @param id the id of the metricDTO to save.
     * @param metricDTO the metricDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metricDTO,
     * or with status {@code 400 (Bad Request)} if the metricDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metricDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/metrics/{id}")
    public ResponseEntity<MetricDTO> updateMetric(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetricDTO metricDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Metric : {}, {}", id, metricDTO);
        if (metricDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metricDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metricRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetricDTO result = metricService.save(metricDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metricDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /metrics/:id} : Partial updates given fields of an existing metric, field will ignore if it is null
     *
     * @param id the id of the metricDTO to save.
     * @param metricDTO the metricDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metricDTO,
     * or with status {@code 400 (Bad Request)} if the metricDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metricDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metricDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/metrics/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MetricDTO> partialUpdateMetric(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MetricDTO metricDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Metric partially : {}, {}", id, metricDTO);
        if (metricDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metricDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metricRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetricDTO> result = metricService.partialUpdate(metricDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metricDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /metrics} : get all the metrics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metrics in body.
     */
    @GetMapping("/metrics")
    public List<MetricDTO> getAllMetrics() {
        log.debug("REST request to get all Metrics");
        return metricService.findAll();
    }

    /**
     * {@code GET  /metrics/:id} : get the "id" metric.
     *
     * @param id the id of the metricDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metricDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/metrics/{id}")
    public ResponseEntity<MetricDTO> getMetric(@PathVariable Long id) {
        log.debug("REST request to get Metric : {}", id);
        Optional<MetricDTO> metricDTO = metricService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metricDTO);
    }

    /**
     * {@code DELETE  /metrics/:id} : delete the "id" metric.
     *
     * @param id the id of the metricDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/metrics/{id}")
    public ResponseEntity<Void> deleteMetric(@PathVariable Long id) {
        log.debug("REST request to delete Metric : {}", id);
        metricService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
