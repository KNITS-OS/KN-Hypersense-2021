package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.ProductionLineRepository;
import com.knits.smartfactory.service.ProductionLineService;
import com.knits.smartfactory.service.dto.ProductionLineDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.ProductionLine}.
 */
@RestController
@RequestMapping("/api")
public class ProductionLineResource {

    private final Logger log = LoggerFactory.getLogger(ProductionLineResource.class);

    private static final String ENTITY_NAME = "productionLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionLineService productionLineService;

    private final ProductionLineRepository productionLineRepository;

    public ProductionLineResource(ProductionLineService productionLineService, ProductionLineRepository productionLineRepository) {
        this.productionLineService = productionLineService;
        this.productionLineRepository = productionLineRepository;
    }

    /**
     * {@code POST  /production-lines} : Create a new productionLine.
     *
     * @param productionLineDTO the productionLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionLineDTO, or with status {@code 400 (Bad Request)} if the productionLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-lines")
    public ResponseEntity<ProductionLineDTO> createProductionLine(@RequestBody ProductionLineDTO productionLineDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductionLine : {}", productionLineDTO);
        if (productionLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new productionLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionLineDTO result = productionLineService.save(productionLineDTO);
        return ResponseEntity
            .created(new URI("/api/production-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-lines/:id} : Updates an existing productionLine.
     *
     * @param id the id of the productionLineDTO to save.
     * @param productionLineDTO the productionLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionLineDTO,
     * or with status {@code 400 (Bad Request)} if the productionLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-lines/{id}")
    public ResponseEntity<ProductionLineDTO> updateProductionLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductionLineDTO productionLineDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductionLine : {}, {}", id, productionLineDTO);
        if (productionLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductionLineDTO result = productionLineService.save(productionLineDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /production-lines/:id} : Partial updates given fields of an existing productionLine, field will ignore if it is null
     *
     * @param id the id of the productionLineDTO to save.
     * @param productionLineDTO the productionLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionLineDTO,
     * or with status {@code 400 (Bad Request)} if the productionLineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productionLineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productionLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/production-lines/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductionLineDTO> partialUpdateProductionLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductionLineDTO productionLineDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductionLine partially : {}, {}", id, productionLineDTO);
        if (productionLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productionLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productionLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductionLineDTO> result = productionLineService.partialUpdate(productionLineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionLineDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /production-lines} : get all the productionLines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionLines in body.
     */
    @GetMapping("/production-lines")
    public List<ProductionLineDTO> getAllProductionLines() {
        log.debug("REST request to get all ProductionLines");
        return productionLineService.findAll();
    }

    /**
     * {@code GET  /production-lines/:id} : get the "id" productionLine.
     *
     * @param id the id of the productionLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-lines/{id}")
    public ResponseEntity<ProductionLineDTO> getProductionLine(@PathVariable Long id) {
        log.debug("REST request to get ProductionLine : {}", id);
        Optional<ProductionLineDTO> productionLineDTO = productionLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionLineDTO);
    }

    /**
     * {@code DELETE  /production-lines/:id} : delete the "id" productionLine.
     *
     * @param id the id of the productionLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-lines/{id}")
    public ResponseEntity<Void> deleteProductionLine(@PathVariable Long id) {
        log.debug("REST request to delete ProductionLine : {}", id);
        productionLineService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
