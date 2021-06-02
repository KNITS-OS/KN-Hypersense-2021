package com.knits.smartfactory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.smartfactory.IntegrationTest;
import com.knits.smartfactory.domain.BusinessUnit;
import com.knits.smartfactory.repository.BusinessUnitRepository;
import com.knits.smartfactory.service.dto.BusinessUnitDTO;
import com.knits.smartfactory.service.mapper.BusinessUnitMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BusinessUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BusinessUnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/business-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private BusinessUnitMapper businessUnitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessUnitMockMvc;

    private BusinessUnit businessUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessUnit createEntity(EntityManager em) {
        BusinessUnit businessUnit = new BusinessUnit().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return businessUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessUnit createUpdatedEntity(EntityManager em) {
        BusinessUnit businessUnit = new BusinessUnit().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return businessUnit;
    }

    @BeforeEach
    public void initTest() {
        businessUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createBusinessUnit() throws Exception {
        int databaseSizeBeforeCreate = businessUnitRepository.findAll().size();
        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);
        restBusinessUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessUnitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBusinessUnit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createBusinessUnitWithExistingId() throws Exception {
        // Create the BusinessUnit with an existing ID
        businessUnit.setId(1L);
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        int databaseSizeBeforeCreate = businessUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBusinessUnits() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        // Get all the businessUnitList
        restBusinessUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getBusinessUnit() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        // Get the businessUnit
        restBusinessUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, businessUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingBusinessUnit() throws Exception {
        // Get the businessUnit
        restBusinessUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBusinessUnit() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();

        // Update the businessUnit
        BusinessUnit updatedBusinessUnit = businessUnitRepository.findById(businessUnit.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessUnit are not directly saved in db
        em.detach(updatedBusinessUnit);
        updatedBusinessUnit.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(updatedBusinessUnit);

        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusinessUnitWithPatch() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();

        // Update the businessUnit using partial update
        BusinessUnit partialUpdatedBusinessUnit = new BusinessUnit();
        partialUpdatedBusinessUnit.setId(businessUnit.getId());

        partialUpdatedBusinessUnit.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessUnit))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateBusinessUnitWithPatch() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();

        // Update the businessUnit using partial update
        BusinessUnit partialUpdatedBusinessUnit = new BusinessUnit();
        partialUpdatedBusinessUnit.setId(businessUnit.getId());

        partialUpdatedBusinessUnit.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessUnit))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, businessUnitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();
        businessUnit.setId(count.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBusinessUnit() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        int databaseSizeBeforeDelete = businessUnitRepository.findAll().size();

        // Delete the businessUnit
        restBusinessUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, businessUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
