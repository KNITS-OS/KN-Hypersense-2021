package com.knits.smartfactory.web.rest;

import com.knits.smartfactory.repository.CompanyUserRepository;
import com.knits.smartfactory.service.CompanyUserService;
import com.knits.smartfactory.service.dto.CompanyUserDTO;
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
 * REST controller for managing {@link com.knits.smartfactory.domain.CompanyUser}.
 */
@RestController
@RequestMapping("/api")
public class CompanyUserResource {

    private final Logger log = LoggerFactory.getLogger(CompanyUserResource.class);

    private static final String ENTITY_NAME = "companyUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyUserService companyUserService;

    private final CompanyUserRepository companyUserRepository;

    public CompanyUserResource(CompanyUserService companyUserService, CompanyUserRepository companyUserRepository) {
        this.companyUserService = companyUserService;
        this.companyUserRepository = companyUserRepository;
    }

    /**
     * {@code POST  /company-users} : Create a new companyUser.
     *
     * @param companyUserDTO the companyUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyUserDTO, or with status {@code 400 (Bad Request)} if the companyUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-users")
    public ResponseEntity<CompanyUserDTO> createCompanyUser(@RequestBody CompanyUserDTO companyUserDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyUser : {}", companyUserDTO);
        if (companyUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyUserDTO result = companyUserService.save(companyUserDTO);
        return ResponseEntity
            .created(new URI("/api/company-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-users/:id} : Updates an existing companyUser.
     *
     * @param id the id of the companyUserDTO to save.
     * @param companyUserDTO the companyUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyUserDTO,
     * or with status {@code 400 (Bad Request)} if the companyUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-users/{id}")
    public ResponseEntity<CompanyUserDTO> updateCompanyUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyUserDTO companyUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyUser : {}, {}", id, companyUserDTO);
        if (companyUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyUserDTO result = companyUserService.save(companyUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-users/:id} : Partial updates given fields of an existing companyUser, field will ignore if it is null
     *
     * @param id the id of the companyUserDTO to save.
     * @param companyUserDTO the companyUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyUserDTO,
     * or with status {@code 400 (Bad Request)} if the companyUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-users/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CompanyUserDTO> partialUpdateCompanyUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyUserDTO companyUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyUser partially : {}, {}", id, companyUserDTO);
        if (companyUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyUserDTO> result = companyUserService.partialUpdate(companyUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-users} : get all the companyUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyUsers in body.
     */
    @GetMapping("/company-users")
    public List<CompanyUserDTO> getAllCompanyUsers() {
        log.debug("REST request to get all CompanyUsers");
        return companyUserService.findAll();
    }

    /**
     * {@code GET  /company-users/:id} : get the "id" companyUser.
     *
     * @param id the id of the companyUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-users/{id}")
    public ResponseEntity<CompanyUserDTO> getCompanyUser(@PathVariable Long id) {
        log.debug("REST request to get CompanyUser : {}", id);
        Optional<CompanyUserDTO> companyUserDTO = companyUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyUserDTO);
    }

    /**
     * {@code DELETE  /company-users/:id} : delete the "id" companyUser.
     *
     * @param id the id of the companyUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-users/{id}")
    public ResponseEntity<Void> deleteCompanyUser(@PathVariable Long id) {
        log.debug("REST request to delete CompanyUser : {}", id);
        companyUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
