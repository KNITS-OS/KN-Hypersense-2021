package com.knits.smartfactory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.smartfactory.IntegrationTest;
import com.knits.smartfactory.domain.MetricData;
import com.knits.smartfactory.repository.MetricDataRepository;
import com.knits.smartfactory.service.dto.MetricDataDTO;
import com.knits.smartfactory.service.mapper.MetricDataMapper;
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
 * Integration tests for the {@link MetricDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetricDataResourceIT {

    private static final String ENTITY_API_URL = "/api/metric-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetricDataRepository metricDataRepository;

    @Autowired
    private MetricDataMapper metricDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetricDataMockMvc;

    private MetricData metricData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetricData createEntity(EntityManager em) {
        MetricData metricData = new MetricData();
        return metricData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetricData createUpdatedEntity(EntityManager em) {
        MetricData metricData = new MetricData();
        return metricData;
    }

    @BeforeEach
    public void initTest() {
        metricData = createEntity(em);
    }

    @Test
    @Transactional
    void createMetricData() throws Exception {
        int databaseSizeBeforeCreate = metricDataRepository.findAll().size();
        // Create the MetricData
        MetricDataDTO metricDataDTO = metricDataMapper.toDto(metricData);
        restMetricDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricDataDTO)))
            .andExpect(status().isCreated());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeCreate + 1);
        MetricData testMetricData = metricDataList.get(metricDataList.size() - 1);
    }

    @Test
    @Transactional
    void createMetricDataWithExistingId() throws Exception {
        // Create the MetricData with an existing ID
        metricData.setId(1L);
        MetricDataDTO metricDataDTO = metricDataMapper.toDto(metricData);

        int databaseSizeBeforeCreate = metricDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetricDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetricData() throws Exception {
        // Initialize the database
        metricDataRepository.saveAndFlush(metricData);

        // Get all the metricDataList
        restMetricDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metricData.getId().intValue())));
    }

    @Test
    @Transactional
    void getMetricData() throws Exception {
        // Initialize the database
        metricDataRepository.saveAndFlush(metricData);

        // Get the metricData
        restMetricDataMockMvc
            .perform(get(ENTITY_API_URL_ID, metricData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metricData.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMetricData() throws Exception {
        // Get the metricData
        restMetricDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMetricData() throws Exception {
        // Initialize the database
        metricDataRepository.saveAndFlush(metricData);

        int databaseSizeBeforeUpdate = metricDataRepository.findAll().size();

        // Update the metricData
        MetricData updatedMetricData = metricDataRepository.findById(metricData.getId()).get();
        // Disconnect from session so that the updates on updatedMetricData are not directly saved in db
        em.detach(updatedMetricData);
        MetricDataDTO metricDataDTO = metricDataMapper.toDto(updatedMetricData);

        restMetricDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metricDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metricDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeUpdate);
        MetricData testMetricData = metricDataList.get(metricDataList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingMetricData() throws Exception {
        int databaseSizeBeforeUpdate = metricDataRepository.findAll().size();
        metricData.setId(count.incrementAndGet());

        // Create the MetricData
        MetricDataDTO metricDataDTO = metricDataMapper.toDto(metricData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetricDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metricDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metricDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetricData() throws Exception {
        int databaseSizeBeforeUpdate = metricDataRepository.findAll().size();
        metricData.setId(count.incrementAndGet());

        // Create the MetricData
        MetricDataDTO metricDataDTO = metricDataMapper.toDto(metricData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metricDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetricData() throws Exception {
        int databaseSizeBeforeUpdate = metricDataRepository.findAll().size();
        metricData.setId(count.incrementAndGet());

        // Create the MetricData
        MetricDataDTO metricDataDTO = metricDataMapper.toDto(metricData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetricDataWithPatch() throws Exception {
        // Initialize the database
        metricDataRepository.saveAndFlush(metricData);

        int databaseSizeBeforeUpdate = metricDataRepository.findAll().size();

        // Update the metricData using partial update
        MetricData partialUpdatedMetricData = new MetricData();
        partialUpdatedMetricData.setId(metricData.getId());

        restMetricDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetricData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetricData))
            )
            .andExpect(status().isOk());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeUpdate);
        MetricData testMetricData = metricDataList.get(metricDataList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateMetricDataWithPatch() throws Exception {
        // Initialize the database
        metricDataRepository.saveAndFlush(metricData);

        int databaseSizeBeforeUpdate = metricDataRepository.findAll().size();

        // Update the metricData using partial update
        MetricData partialUpdatedMetricData = new MetricData();
        partialUpdatedMetricData.setId(metricData.getId());

        restMetricDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetricData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetricData))
            )
            .andExpect(status().isOk());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeUpdate);
        MetricData testMetricData = metricDataList.get(metricDataList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingMetricData() throws Exception {
        int databaseSizeBeforeUpdate = metricDataRepository.findAll().size();
        metricData.setId(count.incrementAndGet());

        // Create the MetricData
        MetricDataDTO metricDataDTO = metricDataMapper.toDto(metricData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetricDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metricDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metricDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetricData() throws Exception {
        int databaseSizeBeforeUpdate = metricDataRepository.findAll().size();
        metricData.setId(count.incrementAndGet());

        // Create the MetricData
        MetricDataDTO metricDataDTO = metricDataMapper.toDto(metricData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metricDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetricData() throws Exception {
        int databaseSizeBeforeUpdate = metricDataRepository.findAll().size();
        metricData.setId(count.incrementAndGet());

        // Create the MetricData
        MetricDataDTO metricDataDTO = metricDataMapper.toDto(metricData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(metricDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetricData in the database
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetricData() throws Exception {
        // Initialize the database
        metricDataRepository.saveAndFlush(metricData);

        int databaseSizeBeforeDelete = metricDataRepository.findAll().size();

        // Delete the metricData
        restMetricDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, metricData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetricData> metricDataList = metricDataRepository.findAll();
        assertThat(metricDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
