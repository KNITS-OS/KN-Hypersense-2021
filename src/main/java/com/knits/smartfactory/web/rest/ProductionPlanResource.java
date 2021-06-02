package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.ProductionPlanRepository;
import com.knits.smartfactory.service.ProductionPlanService;
import com.knits.smartfactory.service.dto.ProductionPlanDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.ProductionPlan}.
 */
@RestController
@RequestMapping("/api")
public class ProductionPlanResource {

    private final Logger log = LoggerFactory.getLogger(ProductionPlanResource.class);

    private static final String ENTITY_NAME = "productionPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionPlanService productionPlanService;

    private final ProductionPlanRepository productionPlanRepository;

    public ProductionPlanResource(ProductionPlanService productionPlanService, ProductionPlanRepository productionPlanRepository) {
        this.productionPlanService = productionPlanService;
        this.productionPlanRepository = productionPlanRepository;
    }

    /**
     * {@code POST  /production-plans} : Create a new productionPlan.
     *
     * @param productionPlanDTO the productionPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionPlanDTO, or with status {@code 400 (Bad Request)} if the productionPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-plans")
    public ResponseEntity<ProductionPlanDTO> createProductionPlan(@RequestBody ProductionPlanDTO productionPlanDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductionPlan : {}", productionPlanDTO);
        if (productionPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new productionPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionPlanDTO result = productionPlanService.save(productionPlanDTO);
        return ResponseEntity
            .created(new URI("/api/production-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-plans/:id} : Updates an existing productionPlan.
     *
     * @param id the id of the productionPlanDTO to save.
     * @param productionPlanDTO the productionPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionPlanDTO,
     * or with status {@code 400 (Bad Request)} if the productionPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-plans/{id}")
    public ResponseEntity<ProductionPlanDTO> updateProductionPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductionPlanDTO productionPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductionPlan : {}, {}", id, productionPlanDTO);
        if (productionPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductionPlanDTO result = productionPlanService.save(productionPlanDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /production-plans/:id} : Partial updates given fields of an existing productionPlan, field will ignore if it is null
     *
     * @param id the id of the productionPlanDTO to save.
     * @param productionPlanDTO the productionPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionPlanDTO,
     * or with status {@code 400 (Bad Request)} if the productionPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productionPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productionPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/production-plans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductionPlanDTO> partialUpdateProductionPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductionPlanDTO productionPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductionPlan partially : {}, {}", id, productionPlanDTO);
        if (productionPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductionPlanDTO> result = productionPlanService.partialUpdate(productionPlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionPlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /production-plans} : get all the productionPlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionPlans in body.
     */
    @GetMapping("/production-plans")
    public List<ProductionPlanDTO> getAllProductionPlans() {
        log.debug("REST request to get all ProductionPlans");
        return productionPlanService.findAll();
    }

    /**
     * {@code GET  /production-plans/:id} : get the "id" productionPlan.
     *
     * @param id the id of the productionPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-plans/{id}")
    public ResponseEntity<ProductionPlanDTO> getProductionPlan(@PathVariable Long id) {
        log.debug("REST request to get ProductionPlan : {}", id);
        Optional<ProductionPlanDTO> productionPlanDTO = productionPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionPlanDTO);
    }

    /**
     * {@code DELETE  /production-plans/:id} : delete the "id" productionPlan.
     *
     * @param id the id of the productionPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-plans/{id}")
    public ResponseEntity<Void> deleteProductionPlan(@PathVariable Long id) {
        log.debug("REST request to delete ProductionPlan : {}", id);
        productionPlanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
