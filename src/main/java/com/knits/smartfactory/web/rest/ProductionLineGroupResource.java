package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.ProductionLineGroupRepository;
import com.knits.smartfactory.service.ProductionLineGroupService;
import com.knits.smartfactory.service.dto.ProductionLineGroupDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.ProductionLineGroup}.
 */
@RestController
@RequestMapping("/api")
public class ProductionLineGroupResource {

    private final Logger log = LoggerFactory.getLogger(ProductionLineGroupResource.class);

    private static final String ENTITY_NAME = "productionLineGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionLineGroupService productionLineGroupService;

    private final ProductionLineGroupRepository productionLineGroupRepository;

    public ProductionLineGroupResource(
        ProductionLineGroupService productionLineGroupService,
        ProductionLineGroupRepository productionLineGroupRepository
    ) {
        this.productionLineGroupService = productionLineGroupService;
        this.productionLineGroupRepository = productionLineGroupRepository;
    }

    /**
     * {@code POST  /production-line-groups} : Create a new productionLineGroup.
     *
     * @param productionLineGroupDTO the productionLineGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionLineGroupDTO, or with status {@code 400 (Bad Request)} if the productionLineGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-line-groups")
    public ResponseEntity<ProductionLineGroupDTO> createProductionLineGroup(@RequestBody ProductionLineGroupDTO productionLineGroupDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductionLineGroup : {}", productionLineGroupDTO);
        if (productionLineGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new productionLineGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionLineGroupDTO result = productionLineGroupService.save(productionLineGroupDTO);
        return ResponseEntity
            .created(new URI("/api/production-line-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-line-groups/:id} : Updates an existing productionLineGroup.
     *
     * @param id the id of the productionLineGroupDTO to save.
     * @param productionLineGroupDTO the productionLineGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionLineGroupDTO,
     * or with status {@code 400 (Bad Request)} if the productionLineGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionLineGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-line-groups/{id}")
    public ResponseEntity<ProductionLineGroupDTO> updateProductionLineGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductionLineGroupDTO productionLineGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductionLineGroup : {}, {}", id, productionLineGroupDTO);
        if (productionLineGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionLineGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionLineGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductionLineGroupDTO result = productionLineGroupService.save(productionLineGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionLineGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /production-line-groups/:id} : Partial updates given fields of an existing productionLineGroup, field will ignore if it is null
     *
     * @param id the id of the productionLineGroupDTO to save.
     * @param productionLineGroupDTO the productionLineGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionLineGroupDTO,
     * or with status {@code 400 (Bad Request)} if the productionLineGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productionLineGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productionLineGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/production-line-groups/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductionLineGroupDTO> partialUpdateProductionLineGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductionLineGroupDTO productionLineGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductionLineGroup partially : {}, {}", id, productionLineGroupDTO);
        if (productionLineGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionLineGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionLineGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductionLineGroupDTO> result = productionLineGroupService.partialUpdate(productionLineGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionLineGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /production-line-groups} : get all the productionLineGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionLineGroups in body.
     */
    @GetMapping("/production-line-groups")
    public List<ProductionLineGroupDTO> getAllProductionLineGroups() {
        log.debug("REST request to get all ProductionLineGroups");
        return productionLineGroupService.findAll();
    }

    /**
     * {@code GET  /production-line-groups/:id} : get the "id" productionLineGroup.
     *
     * @param id the id of the productionLineGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionLineGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-line-groups/{id}")
    public ResponseEntity<ProductionLineGroupDTO> getProductionLineGroup(@PathVariable Long id) {
        log.debug("REST request to get ProductionLineGroup : {}", id);
        Optional<ProductionLineGroupDTO> productionLineGroupDTO = productionLineGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionLineGroupDTO);
    }

    /**
     * {@code DELETE  /production-line-groups/:id} : delete the "id" productionLineGroup.
     *
     * @param id the id of the productionLineGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-line-groups/{id}")
    public ResponseEntity<Void> deleteProductionLineGroup(@PathVariable Long id) {
        log.debug("REST request to delete ProductionLineGroup : {}", id);
        productionLineGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
