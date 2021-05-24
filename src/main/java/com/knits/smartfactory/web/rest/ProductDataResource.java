package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.ProductDataRepository;
import com.knits.smartfactory.service.ProductDataService;
import com.knits.smartfactory.service.dto.ProductDataDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.ProductData}.
 */
@RestController
@RequestMapping("/api")
public class ProductDataResource {

    private final Logger log = LoggerFactory.getLogger(ProductDataResource.class);

    private static final String ENTITY_NAME = "productData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductDataService productDataService;

    private final ProductDataRepository productDataRepository;

    public ProductDataResource(ProductDataService productDataService, ProductDataRepository productDataRepository) {
        this.productDataService = productDataService;
        this.productDataRepository = productDataRepository;
    }

    /**
     * {@code POST  /product-data} : Create a new productData.
     *
     * @param productDataDTO the productDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDataDTO, or with status {@code 400 (Bad Request)} if the productData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-data")
    public ResponseEntity<ProductDataDTO> createProductData(@RequestBody ProductDataDTO productDataDTO) throws URISyntaxException {
        log.debug("REST request to save ProductData : {}", productDataDTO);
        if (productDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new productData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDataDTO result = productDataService.save(productDataDTO);
        return ResponseEntity
            .created(new URI("/api/product-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-data/:id} : Updates an existing productData.
     *
     * @param id the id of the productDataDTO to save.
     * @param productDataDTO the productDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDataDTO,
     * or with status {@code 400 (Bad Request)} if the productDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-data/{id}")
    public ResponseEntity<ProductDataDTO> updateProductData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductDataDTO productDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductData : {}, {}", id, productDataDTO);
        if (productDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductDataDTO result = productDataService.save(productDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-data/:id} : Partial updates given fields of an existing productData, field will ignore if it is null
     *
     * @param id the id of the productDataDTO to save.
     * @param productDataDTO the productDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDataDTO,
     * or with status {@code 400 (Bad Request)} if the productDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductDataDTO> partialUpdateProductData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductDataDTO productDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductData partially : {}, {}", id, productDataDTO);
        if (productDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductDataDTO> result = productDataService.partialUpdate(productDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-data} : get all the productData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productData in body.
     */
    @GetMapping("/product-data")
    public List<ProductDataDTO> getAllProductData() {
        log.debug("REST request to get all ProductData");
        return productDataService.findAll();
    }

    /**
     * {@code GET  /product-data/:id} : get the "id" productData.
     *
     * @param id the id of the productDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-data/{id}")
    public ResponseEntity<ProductDataDTO> getProductData(@PathVariable Long id) {
        log.debug("REST request to get ProductData : {}", id);
        Optional<ProductDataDTO> productDataDTO = productDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDataDTO);
    }

    /**
     * {@code DELETE  /product-data/:id} : delete the "id" productData.
     *
     * @param id the id of the productDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-data/{id}")
    public ResponseEntity<Void> deleteProductData(@PathVariable Long id) {
        log.debug("REST request to delete ProductData : {}", id);
        productDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
