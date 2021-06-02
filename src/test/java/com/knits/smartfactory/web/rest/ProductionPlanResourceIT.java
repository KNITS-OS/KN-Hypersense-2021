package com.knits.smartfactory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.smartfactory.IntegrationTest;
import com.knits.smartfactory.domain.ProductionPlan;
import com.knits.smartfactory.repository.ProductionPlanRepository;
import com.knits.smartfactory.service.dto.ProductionPlanDTO;
import com.knits.smartfactory.service.mapper.ProductionPlanMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ProductionPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductionPlanResourceIT {

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/production-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductionPlanRepository productionPlanRepository;

    @Autowired
    private ProductionPlanMapper productionPlanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionPlanMockMvc;

    private ProductionPlan productionPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlan createEntity(EntityManager em) {
        ProductionPlan productionPlan = new ProductionPlan().dueDate(DEFAULT_DUE_DATE).qty(DEFAULT_QTY).name(DEFAULT_NAME);
        return productionPlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductionPlan createUpdatedEntity(EntityManager em) {
        ProductionPlan productionPlan = new ProductionPlan().dueDate(UPDATED_DUE_DATE).qty(UPDATED_QTY).name(UPDATED_NAME);
        return productionPlan;
    }

    @BeforeEach
    public void initTest() {
        productionPlan = createEntity(em);
    }

    @Test
    @Transactional
    void createProductionPlan() throws Exception {
        int databaseSizeBeforeCreate = productionPlanRepository.findAll().size();
        // Create the ProductionPlan
        ProductionPlanDTO productionPlanDTO = productionPlanMapper.toDto(productionPlan);
        restProductionPlanMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productionPlanDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeCreate + 1);
        ProductionPlan testProductionPlan = productionPlanList.get(productionPlanList.size() - 1);
        assertThat(testProductionPlan.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testProductionPlan.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testProductionPlan.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createProductionPlanWithExistingId() throws Exception {
        // Create the ProductionPlan with an existing ID
        productionPlan.setId(1L);
        ProductionPlanDTO productionPlanDTO = productionPlanMapper.toDto(productionPlan);

        int databaseSizeBeforeCreate = productionPlanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionPlanMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productionPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductionPlans() throws Exception {
        // Initialize the database
        productionPlanRepository.saveAndFlush(productionPlan);

        // Get all the productionPlanList
        restProductionPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productionPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getProductionPlan() throws Exception {
        // Initialize the database
        productionPlanRepository.saveAndFlush(productionPlan);

        // Get the productionPlan
        restProductionPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, productionPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productionPlan.getId().intValue()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingProductionPlan() throws Exception {
        // Get the productionPlan
        restProductionPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductionPlan() throws Exception {
        // Initialize the database
        productionPlanRepository.saveAndFlush(productionPlan);

        int databaseSizeBeforeUpdate = productionPlanRepository.findAll().size();

        // Update the productionPlan
        ProductionPlan updatedProductionPlan = productionPlanRepository.findById(productionPlan.getId()).get();
        // Disconnect from session so that the updates on updatedProductionPlan are not directly saved in db
        em.detach(updatedProductionPlan);
        updatedProductionPlan.dueDate(UPDATED_DUE_DATE).qty(UPDATED_QTY).name(UPDATED_NAME);
        ProductionPlanDTO productionPlanDTO = productionPlanMapper.toDto(updatedProductionPlan);

        restProductionPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productionPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionPlanDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeUpdate);
        ProductionPlan testProductionPlan = productionPlanList.get(productionPlanList.size() - 1);
        assertThat(testProductionPlan.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testProductionPlan.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testProductionPlan.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingProductionPlan() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanRepository.findAll().size();
        productionPlan.setId(count.incrementAndGet());

        // Create the ProductionPlan
        ProductionPlanDTO productionPlanDTO = productionPlanMapper.toDto(productionPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productionPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductionPlan() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanRepository.findAll().size();
        productionPlan.setId(count.incrementAndGet());

        // Create the ProductionPlan
        ProductionPlanDTO productionPlanDTO = productionPlanMapper.toDto(productionPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productionPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductionPlan() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanRepository.findAll().size();
        productionPlan.setId(count.incrementAndGet());

        // Create the ProductionPlan
        ProductionPlanDTO productionPlanDTO = productionPlanMapper.toDto(productionPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionPlanMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productionPlanDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductionPlanWithPatch() throws Exception {
        // Initialize the database
        productionPlanRepository.saveAndFlush(productionPlan);

        int databaseSizeBeforeUpdate = productionPlanRepository.findAll().size();

        // Update the productionPlan using partial update
        ProductionPlan partialUpdatedProductionPlan = new ProductionPlan();
        partialUpdatedProductionPlan.setId(productionPlan.getId());

        partialUpdatedProductionPlan.dueDate(UPDATED_DUE_DATE);

        restProductionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductionPlan))
            )
            .andExpect(status().isOk());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeUpdate);
        ProductionPlan testProductionPlan = productionPlanList.get(productionPlanList.size() - 1);
        assertThat(testProductionPlan.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testProductionPlan.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testProductionPlan.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateProductionPlanWithPatch() throws Exception {
        // Initialize the database
        productionPlanRepository.saveAndFlush(productionPlan);

        int databaseSizeBeforeUpdate = productionPlanRepository.findAll().size();

        // Update the productionPlan using partial update
        ProductionPlan partialUpdatedProductionPlan = new ProductionPlan();
        partialUpdatedProductionPlan.setId(productionPlan.getId());

        partialUpdatedProductionPlan.dueDate(UPDATED_DUE_DATE).qty(UPDATED_QTY).name(UPDATED_NAME);

        restProductionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductionPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductionPlan))
            )
            .andExpect(status().isOk());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeUpdate);
        ProductionPlan testProductionPlan = productionPlanList.get(productionPlanList.size() - 1);
        assertThat(testProductionPlan.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testProductionPlan.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testProductionPlan.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingProductionPlan() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanRepository.findAll().size();
        productionPlan.setId(count.incrementAndGet());

        // Create the ProductionPlan
        ProductionPlanDTO productionPlanDTO = productionPlanMapper.toDto(productionPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productionPlanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductionPlan() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanRepository.findAll().size();
        productionPlan.setId(count.incrementAndGet());

        // Create the ProductionPlan
        ProductionPlanDTO productionPlanDTO = productionPlanMapper.toDto(productionPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductionPlan() throws Exception {
        int databaseSizeBeforeUpdate = productionPlanRepository.findAll().size();
        productionPlan.setId(count.incrementAndGet());

        // Create the ProductionPlan
        ProductionPlanDTO productionPlanDTO = productionPlanMapper.toDto(productionPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductionPlanMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productionPlanDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductionPlan in the database
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductionPlan() throws Exception {
        // Initialize the database
        productionPlanRepository.saveAndFlush(productionPlan);

        int databaseSizeBeforeDelete = productionPlanRepository.findAll().size();

        // Delete the productionPlan
        restProductionPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, productionPlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductionPlan> productionPlanList = productionPlanRepository.findAll();
        assertThat(productionPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
