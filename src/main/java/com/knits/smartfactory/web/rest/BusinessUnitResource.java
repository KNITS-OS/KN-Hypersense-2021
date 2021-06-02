package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.BusinessUnitRepository;
import com.knits.smartfactory.service.BusinessUnitService;
import com.knits.smartfactory.service.dto.BusinessUnitDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.BusinessUnit}.
 */
@RestController
@RequestMapping("/api")
public class BusinessUnitResource {

    private final Logger log = LoggerFactory.getLogger(BusinessUnitResource.class);

    private static final String ENTITY_NAME = "businessUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessUnitService businessUnitService;

    private final BusinessUnitRepository businessUnitRepository;

    public BusinessUnitResource(BusinessUnitService businessUnitService, BusinessUnitRepository businessUnitRepository) {
        this.businessUnitService = businessUnitService;
        this.businessUnitRepository = businessUnitRepository;
    }

    /**
     * {@code POST  /business-units} : Create a new businessUnit.
     *
     * @param businessUnitDTO the businessUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessUnitDTO, or with status {@code 400 (Bad Request)} if the businessUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-units")
    public ResponseEntity<BusinessUnitDTO> createBusinessUnit(@RequestBody BusinessUnitDTO businessUnitDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessUnit : {}", businessUnitDTO);
        if (businessUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessUnitDTO result = businessUnitService.save(businessUnitDTO);
        return ResponseEntity
            .created(new URI("/api/business-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-units/:id} : Updates an existing businessUnit.
     *
     * @param id the id of the businessUnitDTO to save.
     * @param businessUnitDTO the businessUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessUnitDTO,
     * or with status {@code 400 (Bad Request)} if the businessUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-units/{id}")
    public ResponseEntity<BusinessUnitDTO> updateBusinessUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BusinessUnitDTO businessUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BusinessUnit : {}, {}", id, businessUnitDTO);
        if (businessUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BusinessUnitDTO result = businessUnitService.save(businessUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /business-units/:id} : Partial updates given fields of an existing businessUnit, field will ignore if it is null
     *
     * @param id the id of the businessUnitDTO to save.
     * @param businessUnitDTO the businessUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessUnitDTO,
     * or with status {@code 400 (Bad Request)} if the businessUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/business-units/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BusinessUnitDTO> partialUpdateBusinessUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BusinessUnitDTO businessUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BusinessUnit partially : {}, {}", id, businessUnitDTO);
        if (businessUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusinessUnitDTO> result = businessUnitService.partialUpdate(businessUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /business-units} : get all the businessUnits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessUnits in body.
     */
    @GetMapping("/business-units")
    public List<BusinessUnitDTO> getAllBusinessUnits() {
        log.debug("REST request to get all BusinessUnits");
        return businessUnitService.findAll();
    }

    /**
     * {@code GET  /business-units/:id} : get the "id" businessUnit.
     *
     * @param id the id of the businessUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-units/{id}")
    public ResponseEntity<BusinessUnitDTO> getBusinessUnit(@PathVariable Long id) {
        log.debug("REST request to get BusinessUnit : {}", id);
        Optional<BusinessUnitDTO> businessUnitDTO = businessUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessUnitDTO);
    }

    /**
     * {@code DELETE  /business-units/:id} : delete the "id" businessUnit.
     *
     * @param id the id of the businessUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-units/{id}")
    public ResponseEntity<Void> deleteBusinessUnit(@PathVariable Long id) {
        log.debug("REST request to delete BusinessUnit : {}", id);
        businessUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
