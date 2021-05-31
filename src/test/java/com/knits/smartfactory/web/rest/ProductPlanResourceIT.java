package com.knits.smartfactory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.smartfactory.IntegrationTest;
import com.knits.smartfactory.domain.ProductPlan;
import com.knits.smartfactory.repository.ProductPlanRepository;
import com.knits.smartfactory.service.dto.ProductPlanDTO;
import com.knits.smartfactory.service.mapper.ProductPlanMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ProductPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductPlanResourceIT {

    private static final LocalDate DEFAULT_ESTIMATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_QTY = 1L;
    private static final Long UPDATED_QTY = 2L;

    private static final String ENTITY_API_URL = "/api/product-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductPlanRepository productPlanRepository;

    @Autowired
    private ProductPlanMapper productPlanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductPlanMockMvc;

    private ProductPlan productPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPlan createEntity(EntityManager em) {
        ProductPlan productPlan = new ProductPlan().estimatedTime(DEFAULT_ESTIMATED_TIME).qty(DEFAULT_QTY);
        return productPlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPlan createUpdatedEntity(EntityManager em) {
        ProductPlan productPlan = new ProductPlan().estimatedTime(UPDATED_ESTIMATED_TIME).qty(UPDATED_QTY);
        return productPlan;
    }

    @BeforeEach
    public void initTest() {
        productPlan = createEntity(em);
    }

    @Test
    @Transactional
    void createProductPlan() throws Exception {
        int databaseSizeBeforeCreate = productPlanRepository.findAll().size();
        // Create the ProductPlan
        ProductPlanDTO productPlanDTO = productPlanMapper.toDto(productPlan);
        restProductPlanMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productPlanDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeCreate + 1);
        ProductPlan testProductPlan = productPlanList.get(productPlanList.size() - 1);
        assertThat(testProductPlan.getEstimatedTime()).isEqualTo(DEFAULT_ESTIMATED_TIME);
        assertThat(testProductPlan.getQty()).isEqualTo(DEFAULT_QTY);
    }

    @Test
    @Transactional
    void createProductPlanWithExistingId() throws Exception {
        // Create the ProductPlan with an existing ID
        productPlan.setId(1L);
        ProductPlanDTO productPlanDTO = productPlanMapper.toDto(productPlan);

        int databaseSizeBeforeCreate = productPlanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPlanMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductPlans() throws Exception {
        // Initialize the database
        productPlanRepository.saveAndFlush(productPlan);

        // Get all the productPlanList
        restProductPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].estimatedTime").value(hasItem(DEFAULT_ESTIMATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.intValue())));
    }

    @Test
    @Transactional
    void getProductPlan() throws Exception {
        // Initialize the database
        productPlanRepository.saveAndFlush(productPlan);

        // Get the productPlan
        restProductPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, productPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productPlan.getId().intValue()))
            .andExpect(jsonPath("$.estimatedTime").value(DEFAULT_ESTIMATED_TIME.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProductPlan() throws Exception {
        // Get the productPlan
        restProductPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductPlan() throws Exception {
        // Initialize the database
        productPlanRepository.saveAndFlush(productPlan);

        int databaseSizeBeforeUpdate = productPlanRepository.findAll().size();

        // Update the productPlan
        ProductPlan updatedProductPlan = productPlanRepository.findById(productPlan.getId()).get();
        // Disconnect from session so that the updates on updatedProductPlan are not directly saved in db
        em.detach(updatedProductPlan);
        updatedProductPlan.estimatedTime(UPDATED_ESTIMATED_TIME).qty(UPDATED_QTY);
        ProductPlanDTO productPlanDTO = productPlanMapper.toDto(updatedProductPlan);

        restProductPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productPlanDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeUpdate);
        ProductPlan testProductPlan = productPlanList.get(productPlanList.size() - 1);
        assertThat(testProductPlan.getEstimatedTime()).isEqualTo(UPDATED_ESTIMATED_TIME);
        assertThat(testProductPlan.getQty()).isEqualTo(UPDATED_QTY);
    }

    @Test
    @Transactional
    void putNonExistingProductPlan() throws Exception {
        int databaseSizeBeforeUpdate = productPlanRepository.findAll().size();
        productPlan.setId(count.incrementAndGet());

        // Create the ProductPlan
        ProductPlanDTO productPlanDTO = productPlanMapper.toDto(productPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductPlan() throws Exception {
        int databaseSizeBeforeUpdate = productPlanRepository.findAll().size();
        productPlan.setId(count.incrementAndGet());

        // Create the ProductPlan
        ProductPlanDTO productPlanDTO = productPlanMapper.toDto(productPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductPlan() throws Exception {
        int databaseSizeBeforeUpdate = productPlanRepository.findAll().size();
        productPlan.setId(count.incrementAndGet());

        // Create the ProductPlan
        ProductPlanDTO productPlanDTO = productPlanMapper.toDto(productPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productPlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductPlanWithPatch() throws Exception {
        // Initialize the database
        productPlanRepository.saveAndFlush(productPlan);

        int databaseSizeBeforeUpdate = productPlanRepository.findAll().size();

        // Update the productPlan using partial update
        ProductPlan partialUpdatedProductPlan = new ProductPlan();
        partialUpdatedProductPlan.setId(productPlan.getId());

        partialUpdatedProductPlan.qty(UPDATED_QTY);

        restProductPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductPlan))
            )
            .andExpect(status().isOk());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeUpdate);
        ProductPlan testProductPlan = productPlanList.get(productPlanList.size() - 1);
        assertThat(testProductPlan.getEstimatedTime()).isEqualTo(DEFAULT_ESTIMATED_TIME);
        assertThat(testProductPlan.getQty()).isEqualTo(UPDATED_QTY);
    }

    @Test
    @Transactional
    void fullUpdateProductPlanWithPatch() throws Exception {
        // Initialize the database
        productPlanRepository.saveAndFlush(productPlan);

        int databaseSizeBeforeUpdate = productPlanRepository.findAll().size();

        // Update the productPlan using partial update
        ProductPlan partialUpdatedProductPlan = new ProductPlan();
        partialUpdatedProductPlan.setId(productPlan.getId());

        partialUpdatedProductPlan.estimatedTime(UPDATED_ESTIMATED_TIME).qty(UPDATED_QTY);

        restProductPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductPlan))
            )
            .andExpect(status().isOk());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeUpdate);
        ProductPlan testProductPlan = productPlanList.get(productPlanList.size() - 1);
        assertThat(testProductPlan.getEstimatedTime()).isEqualTo(UPDATED_ESTIMATED_TIME);
        assertThat(testProductPlan.getQty()).isEqualTo(UPDATED_QTY);
    }

    @Test
    @Transactional
    void patchNonExistingProductPlan() throws Exception {
        int databaseSizeBeforeUpdate = productPlanRepository.findAll().size();
        productPlan.setId(count.incrementAndGet());

        // Create the ProductPlan
        ProductPlanDTO productPlanDTO = productPlanMapper.toDto(productPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productPlanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductPlan() throws Exception {
        int databaseSizeBeforeUpdate = productPlanRepository.findAll().size();
        productPlan.setId(count.incrementAndGet());

        // Create the ProductPlan
        ProductPlanDTO productPlanDTO = productPlanMapper.toDto(productPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductPlan() throws Exception {
        int databaseSizeBeforeUpdate = productPlanRepository.findAll().size();
        productPlan.setId(count.incrementAndGet());

        // Create the ProductPlan
        ProductPlanDTO productPlanDTO = productPlanMapper.toDto(productPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPlanMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productPlanDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductPlan in the database
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductPlan() throws Exception {
        // Initialize the database
        productPlanRepository.saveAndFlush(productPlan);

        int databaseSizeBeforeDelete = productPlanRepository.findAll().size();

        // Delete the productPlan
        restProductPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, productPlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductPlan> productPlanList = productPlanRepository.findAll();
        assertThat(productPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
