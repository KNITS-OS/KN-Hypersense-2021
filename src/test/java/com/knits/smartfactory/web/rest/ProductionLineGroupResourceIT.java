package com.knits.smartfactory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.smartfactory.IntegrationTest;
import com.knits.smartfactory.domain.ProductionLineGroup;
import com.knits.smartfactory.repository.ProductionLineGroupRepository;
import com.knits.smartfactory.service.dto.ProductionLineGroupDTO;
import com.knits.smartfactory.service.mapper.ProductionLineGroupMapper;
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
 * Integration tests for the {@link ProductionLineGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductionLineGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/production-line-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductionLineGroupRepository productionLineGroupRepository;

    @Autowired
    private ProductionLineGroupMapper productionLineGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionLineGroupMockMvc;

    private ProductionLineGroup productionLineGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionLineGroup createEntity(EntityManager em) {
        ProductionLineGroup productionLineGroup = new ProductionLineGroup().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return productionLineGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionLineGroup createUpdatedEntity(EntityManager em) {
        ProductionLineGroup productionLineGroup = new ProductionLineGroup().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return productionLineGroup;
    }

    @BeforeEach
    public void initTest() {
        productionLineGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createProductionLineGroup() throws Exception {
        int databaseSizeBeforeCreate = productionLineGroupRepository.findAll().size();
        // Create the ProductionLineGroup
        ProductionLineGroupDTO productionLineGroupDTO = productionLineGroupMapper.toDto(productionLineGroup);
        restProductionLineGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionLineGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionLineGroup testProductionLineGroup = productionLineGroupList.get(productionLineGroupList.size() - 1);
        assertThat(testProductionLineGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductionLineGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createProductionLineGroupWithExistingId() throws Exception {
        // Create the ProductionLineGroup with an existing ID
        productionLineGroup.setId(1L);
        ProductionLineGroupDTO productionLineGroupDTO = productionLineGroupMapper.toDto(productionLineGroup);

        int databaseSizeBeforeCreate = productionLineGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionLineGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionLineGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductionLineGroups() throws Exception {
        // Initialize the database
        productionLineGroupRepository.saveAndFlush(productionLineGroup);

        // Get all the productionLineGroupList
        restProductionLineGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionLineGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getProductionLineGroup() throws Exception {
        // Initialize the database
        productionLineGroupRepository.saveAndFlush(productionLineGroup);

        // Get the productionLineGroup
        restProductionLineGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, productionLineGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionLineGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingProductionLineGroup() throws Exception {
        // Get the productionLineGroup
        restProductionLineGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductionLineGroup() throws Exception {
        // Initialize the database
        productionLineGroupRepository.saveAndFlush(productionLineGroup);

        int databaseSizeBeforeUpdate = productionLineGroupRepository.findAll().size();

        // Update the productionLineGroup
        ProductionLineGroup updatedProductionLineGroup = productionLineGroupRepository.findById(productionLineGroup.getId()).get();
        // Disconnect from session so that the updates on updatedProductionLineGroup are not directly saved in db
        em.detach(updatedProductionLineGroup);
        updatedProductionLineGroup.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        ProductionLineGroupDTO productionLineGroupDTO = productionLineGroupMapper.toDto(updatedProductionLineGroup);

        restProductionLineGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productionLineGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionLineGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeUpdate);
        ProductionLineGroup testProductionLineGroup = productionLineGroupList.get(productionLineGroupList.size() - 1);
        assertThat(testProductionLineGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductionLineGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingProductionLineGroup() throws Exception {
        int databaseSizeBeforeUpdate = productionLineGroupRepository.findAll().size();
        productionLineGroup.setId(count.incrementAndGet());

        // Create the ProductionLineGroup
        ProductionLineGroupDTO productionLineGroupDTO = productionLineGroupMapper.toDto(productionLineGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionLineGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productionLineGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionLineGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductionLineGroup() throws Exception {
        int databaseSizeBeforeUpdate = productionLineGroupRepository.findAll().size();
        productionLineGroup.setId(count.incrementAndGet());

        // Create the ProductionLineGroup
        ProductionLineGroupDTO productionLineGroupDTO = productionLineGroupMapper.toDto(productionLineGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionLineGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionLineGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductionLineGroup() throws Exception {
        int databaseSizeBeforeUpdate = productionLineGroupRepository.findAll().size();
        productionLineGroup.setId(count.incrementAndGet());

        // Create the ProductionLineGroup
        ProductionLineGroupDTO productionLineGroupDTO = productionLineGroupMapper.toDto(productionLineGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionLineGroupMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionLineGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductionLineGroupWithPatch() throws Exception {
        // Initialize the database
        productionLineGroupRepository.saveAndFlush(productionLineGroup);

        int databaseSizeBeforeUpdate = productionLineGroupRepository.findAll().size();

        // Update the productionLineGroup using partial update
        ProductionLineGroup partialUpdatedProductionLineGroup = new ProductionLineGroup();
        partialUpdatedProductionLineGroup.setId(productionLineGroup.getId());

        partialUpdatedProductionLineGroup.description(UPDATED_DESCRIPTION);

        restProductionLineGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionLineGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductionLineGroup))
            )
            .andExpect(status().isOk());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeUpdate);
        ProductionLineGroup testProductionLineGroup = productionLineGroupList.get(productionLineGroupList.size() - 1);
        assertThat(testProductionLineGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductionLineGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateProductionLineGroupWithPatch() throws Exception {
        // Initialize the database
        productionLineGroupRepository.saveAndFlush(productionLineGroup);

        int databaseSizeBeforeUpdate = productionLineGroupRepository.findAll().size();

        // Update the productionLineGroup using partial update
        ProductionLineGroup partialUpdatedProductionLineGroup = new ProductionLineGroup();
        partialUpdatedProductionLineGroup.setId(productionLineGroup.getId());

        partialUpdatedProductionLineGroup.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restProductionLineGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionLineGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductionLineGroup))
            )
            .andExpect(status().isOk());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeUpdate);
        ProductionLineGroup testProductionLineGroup = productionLineGroupList.get(productionLineGroupList.size() - 1);
        assertThat(testProductionLineGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductionLineGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingProductionLineGroup() throws Exception {
        int databaseSizeBeforeUpdate = productionLineGroupRepository.findAll().size();
        productionLineGroup.setId(count.incrementAndGet());

        // Create the ProductionLineGroup
        ProductionLineGroupDTO productionLineGroupDTO = productionLineGroupMapper.toDto(productionLineGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionLineGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productionLineGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionLineGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductionLineGroup() throws Exception {
        int databaseSizeBeforeUpdate = productionLineGroupRepository.findAll().size();
        productionLineGroup.setId(count.incrementAndGet());

        // Create the ProductionLineGroup
        ProductionLineGroupDTO productionLineGroupDTO = productionLineGroupMapper.toDto(productionLineGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionLineGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionLineGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductionLineGroup() throws Exception {
        int databaseSizeBeforeUpdate = productionLineGroupRepository.findAll().size();
        productionLineGroup.setId(count.incrementAndGet());

        // Create the ProductionLineGroup
        ProductionLineGroupDTO productionLineGroupDTO = productionLineGroupMapper.toDto(productionLineGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionLineGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionLineGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionLineGroup in the database
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductionLineGroup() throws Exception {
        // Initialize the database
        productionLineGroupRepository.saveAndFlush(productionLineGroup);

        int databaseSizeBeforeDelete = productionLineGroupRepository.findAll().size();

        // Delete the productionLineGroup
        restProductionLineGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, productionLineGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionLineGroup> productionLineGroupList = productionLineGroupRepository.findAll();
        assertThat(productionLineGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
