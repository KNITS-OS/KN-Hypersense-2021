package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.ProductPlanRepository;
import com.knits.smartfactory.service.ProductPlanService;
import com.knits.smartfactory.service.dto.ProductPlanDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.ProductPlan}.
 */
@RestController
@RequestMapping("/api")
public class ProductPlanResource {

    private final Logger log = LoggerFactory.getLogger(ProductPlanResource.class);

    private static final String ENTITY_NAME = "productPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductPlanService productPlanService;

    private final ProductPlanRepository productPlanRepository;

    public ProductPlanResource(ProductPlanService productPlanService, ProductPlanRepository productPlanRepository) {
        this.productPlanService = productPlanService;
        this.productPlanRepository = productPlanRepository;
    }

    /**
     * {@code POST  /product-plans} : Create a new productPlan.
     *
     * @param productPlanDTO the productPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productPlanDTO, or with status {@code 400 (Bad Request)} if the productPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-plans")
    public ResponseEntity<ProductPlanDTO> createProductPlan(@RequestBody ProductPlanDTO productPlanDTO) throws URISyntaxException {
        log.debug("REST request to save ProductPlan : {}", productPlanDTO);
        if (productPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new productPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductPlanDTO result = productPlanService.save(productPlanDTO);
        return ResponseEntity
            .created(new URI("/api/product-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-plans/:id} : Updates an existing productPlan.
     *
     * @param id the id of the productPlanDTO to save.
     * @param productPlanDTO the productPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPlanDTO,
     * or with status {@code 400 (Bad Request)} if the productPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-plans/{id}")
    public ResponseEntity<ProductPlanDTO> updateProductPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductPlanDTO productPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductPlan : {}, {}", id, productPlanDTO);
        if (productPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductPlanDTO result = productPlanService.save(productPlanDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-plans/:id} : Partial updates given fields of an existing productPlan, field will ignore if it is null
     *
     * @param id the id of the productPlanDTO to save.
     * @param productPlanDTO the productPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productPlanDTO,
     * or with status {@code 400 (Bad Request)} if the productPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-plans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductPlanDTO> partialUpdateProductPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductPlanDTO productPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductPlan partially : {}, {}", id, productPlanDTO);
        if (productPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductPlanDTO> result = productPlanService.partialUpdate(productPlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productPlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-plans} : get all the productPlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productPlans in body.
     */
    @GetMapping("/product-plans")
    public List<ProductPlanDTO> getAllProductPlans() {
        log.debug("REST request to get all ProductPlans");
        return productPlanService.findAll();
    }

    /**
     * {@code GET  /product-plans/:id} : get the "id" productPlan.
     *
     * @param id the id of the productPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-plans/{id}")
    public ResponseEntity<ProductPlanDTO> getProductPlan(@PathVariable Long id) {
        log.debug("REST request to get ProductPlan : {}", id);
        Optional<ProductPlanDTO> productPlanDTO = productPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productPlanDTO);
    }

    /**
     * {@code DELETE  /product-plans/:id} : delete the "id" productPlan.
     *
     * @param id the id of the productPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-plans/{id}")
    public ResponseEntity<Void> deleteProductPlan(@PathVariable Long id) {
        log.debug("REST request to delete ProductPlan : {}", id);
        productPlanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
