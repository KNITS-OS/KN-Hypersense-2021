package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.ThingsRepository;
import com.knits.smartfactory.service.ThingsService;
import com.knits.smartfactory.service.dto.ThingsDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.Things}.
 */
@RestController
@RequestMapping("/api")
public class ThingsResource {

    private final Logger log = LoggerFactory.getLogger(ThingsResource.class);

    private static final String ENTITY_NAME = "things";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThingsService thingsService;

    private final ThingsRepository thingsRepository;

    public ThingsResource(ThingsService thingsService, ThingsRepository thingsRepository) {
        this.thingsService = thingsService;
        this.thingsRepository = thingsRepository;
    }

    /**
     * {@code POST  /things} : Create a new things.
     *
     * @param thingsDTO the thingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thingsDTO, or with status {@code 400 (Bad Request)} if the things has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/things")
    public ResponseEntity<ThingsDTO> createThings(@RequestBody ThingsDTO thingsDTO) throws URISyntaxException {
        log.debug("REST request to save Things : {}", thingsDTO);
        if (thingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new things cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThingsDTO result = thingsService.save(thingsDTO);
        return ResponseEntity
            .created(new URI("/api/things/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /things/:id} : Updates an existing things.
     *
     * @param id the id of the thingsDTO to save.
     * @param thingsDTO the thingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thingsDTO,
     * or with status {@code 400 (Bad Request)} if the thingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/things/{id}")
    public ResponseEntity<ThingsDTO> updateThings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThingsDTO thingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Things : {}, {}", id, thingsDTO);
        if (thingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ThingsDTO result = thingsService.save(thingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /things/:id} : Partial updates given fields of an existing things, field will ignore if it is null
     *
     * @param id the id of the thingsDTO to save.
     * @param thingsDTO the thingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thingsDTO,
     * or with status {@code 400 (Bad Request)} if the thingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the thingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the thingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/things/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ThingsDTO> partialUpdateThings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThingsDTO thingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Things partially : {}, {}", id, thingsDTO);
        if (thingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThingsDTO> result = thingsService.partialUpdate(thingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /things} : get all the things.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of things in body.
     */
    @GetMapping("/things")
    public List<ThingsDTO> getAllThings() {
        log.debug("REST request to get all Things");
        return thingsService.findAll();
    }

    /**
     * {@code GET  /things/:id} : get the "id" things.
     *
     * @param id the id of the thingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/things/{id}")
    public ResponseEntity<ThingsDTO> getThings(@PathVariable Long id) {
        log.debug("REST request to get Things : {}", id);
        Optional<ThingsDTO> thingsDTO = thingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thingsDTO);
    }

    /**
     * {@code DELETE  /things/:id} : delete the "id" things.
     *
     * @param id the id of the thingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/things/{id}")
    public ResponseEntity<Void> deleteThings(@PathVariable Long id) {
        log.debug("REST request to delete Things : {}", id);
        thingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
