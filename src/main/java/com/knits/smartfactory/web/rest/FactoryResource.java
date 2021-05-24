package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.FactoryRepository;
import com.knits.smartfactory.service.FactoryService;
import com.knits.smartfactory.service.dto.FactoryDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.Factory}.
 */
@RestController
@RequestMapping("/api")
public class FactoryResource {

    private final Logger log = LoggerFactory.getLogger(FactoryResource.class);

    private static final String ENTITY_NAME = "factory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactoryService factoryService;

    private final FactoryRepository factoryRepository;

    public FactoryResource(FactoryService factoryService, FactoryRepository factoryRepository) {
        this.factoryService = factoryService;
        this.factoryRepository = factoryRepository;
    }

    /**
     * {@code POST  /factories} : Create a new factory.
     *
     * @param factoryDTO the factoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factoryDTO, or with status {@code 400 (Bad Request)} if the factory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/factories")
    public ResponseEntity<FactoryDTO> createFactory(@RequestBody FactoryDTO factoryDTO) throws URISyntaxException {
        log.debug("REST request to save Factory : {}", factoryDTO);
        if (factoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new factory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactoryDTO result = factoryService.save(factoryDTO);
        return ResponseEntity
            .created(new URI("/api/factories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /factories/:id} : Updates an existing factory.
     *
     * @param id the id of the factoryDTO to save.
     * @param factoryDTO the factoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factoryDTO,
     * or with status {@code 400 (Bad Request)} if the factoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/factories/{id}")
    public ResponseEntity<FactoryDTO> updateFactory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FactoryDTO factoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Factory : {}, {}", id, factoryDTO);
        if (factoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FactoryDTO result = factoryService.save(factoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /factories/:id} : Partial updates given fields of an existing factory, field will ignore if it is null
     *
     * @param id the id of the factoryDTO to save.
     * @param factoryDTO the factoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factoryDTO,
     * or with status {@code 400 (Bad Request)} if the factoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the factoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the factoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/factories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FactoryDTO> partialUpdateFactory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FactoryDTO factoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Factory partially : {}, {}", id, factoryDTO);
        if (factoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FactoryDTO> result = factoryService.partialUpdate(factoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /factories} : get all the factories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factories in body.
     */
    @GetMapping("/factories")
    public List<FactoryDTO> getAllFactories() {
        log.debug("REST request to get all Factories");
        return factoryService.findAll();
    }

    /**
     * {@code GET  /factories/:id} : get the "id" factory.
     *
     * @param id the id of the factoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/factories/{id}")
    public ResponseEntity<FactoryDTO> getFactory(@PathVariable Long id) {
        log.debug("REST request to get Factory : {}", id);
        Optional<FactoryDTO> factoryDTO = factoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factoryDTO);
    }

    /**
     * {@code DELETE  /factories/:id} : delete the "id" factory.
     *
     * @param id the id of the factoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/factories/{id}")
    public ResponseEntity<Void> deleteFactory(@PathVariable Long id) {
        log.debug("REST request to delete Factory : {}", id);
        factoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
