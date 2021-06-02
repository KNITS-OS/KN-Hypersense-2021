package com.knits.smartfactory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.smartfactory.IntegrationTest;
import com.knits.smartfactory.domain.Metric;
import com.knits.smartfactory.repository.MetricRepository;
import com.knits.smartfactory.service.dto.MetricDTO;
import com.knits.smartfactory.service.mapper.MetricMapper;
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
 * Integration tests for the {@link MetricResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetricResourceIT {

    private static final String DEFAULT_THING_UUID = "AAAAAAAAAA";
    private static final String UPDATED_THING_UUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/metrics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private MetricMapper metricMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetricMockMvc;

    private Metric metric;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Metric createEntity(EntityManager em) {
        Metric metric = new Metric().thingUuid(DEFAULT_THING_UUID);
        return metric;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Metric createUpdatedEntity(EntityManager em) {
        Metric metric = new Metric().thingUuid(UPDATED_THING_UUID);
        return metric;
    }

    @BeforeEach
    public void initTest() {
        metric = createEntity(em);
    }

    @Test
    @Transactional
    void createMetric() throws Exception {
        int databaseSizeBeforeCreate = metricRepository.findAll().size();
        // Create the Metric
        MetricDTO metricDTO = metricMapper.toDto(metric);
        restMetricMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricDTO)))
            .andExpect(status().isCreated());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeCreate + 1);
        Metric testMetric = metricList.get(metricList.size() - 1);
        assertThat(testMetric.getThingUuid()).isEqualTo(DEFAULT_THING_UUID);
    }

    @Test
    @Transactional
    void createMetricWithExistingId() throws Exception {
        // Create the Metric with an existing ID
        metric.setId(1L);
        MetricDTO metricDTO = metricMapper.toDto(metric);

        int databaseSizeBeforeCreate = metricRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetricMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMetrics() throws Exception {
        // Initialize the database
        metricRepository.saveAndFlush(metric);

        // Get all the metricList
        restMetricMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metric.getId().intValue())))
            .andExpect(jsonPath("$.[*].thingUuid").value(hasItem(DEFAULT_THING_UUID)));
    }

    @Test
    @Transactional
    void getMetric() throws Exception {
        // Initialize the database
        metricRepository.saveAndFlush(metric);

        // Get the metric
        restMetricMockMvc
            .perform(get(ENTITY_API_URL_ID, metric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metric.getId().intValue()))
            .andExpect(jsonPath("$.thingUuid").value(DEFAULT_THING_UUID));
    }

    @Test
    @Transactional
    void getNonExistingMetric() throws Exception {
        // Get the metric
        restMetricMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMetric() throws Exception {
        // Initialize the database
        metricRepository.saveAndFlush(metric);

        int databaseSizeBeforeUpdate = metricRepository.findAll().size();

        // Update the metric
        Metric updatedMetric = metricRepository.findById(metric.getId()).get();
        // Disconnect from session so that the updates on updatedMetric are not directly saved in db
        em.detach(updatedMetric);
        updatedMetric.thingUuid(UPDATED_THING_UUID);
        MetricDTO metricDTO = metricMapper.toDto(updatedMetric);

        restMetricMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metricDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metricDTO))
            )
            .andExpect(status().isOk());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeUpdate);
        Metric testMetric = metricList.get(metricList.size() - 1);
        assertThat(testMetric.getThingUuid()).isEqualTo(UPDATED_THING_UUID);
    }

    @Test
    @Transactional
    void putNonExistingMetric() throws Exception {
        int databaseSizeBeforeUpdate = metricRepository.findAll().size();
        metric.setId(count.incrementAndGet());

        // Create the Metric
        MetricDTO metricDTO = metricMapper.toDto(metric);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetricMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metricDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metricDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetric() throws Exception {
        int databaseSizeBeforeUpdate = metricRepository.findAll().size();
        metric.setId(count.incrementAndGet());

        // Create the Metric
        MetricDTO metricDTO = metricMapper.toDto(metric);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metricDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetric() throws Exception {
        int databaseSizeBeforeUpdate = metricRepository.findAll().size();
        metric.setId(count.incrementAndGet());

        // Create the Metric
        MetricDTO metricDTO = metricMapper.toDto(metric);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metricDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetricWithPatch() throws Exception {
        // Initialize the database
        metricRepository.saveAndFlush(metric);

        int databaseSizeBeforeUpdate = metricRepository.findAll().size();

        // Update the metric using partial update
        Metric partialUpdatedMetric = new Metric();
        partialUpdatedMetric.setId(metric.getId());

        partialUpdatedMetric.thingUuid(UPDATED_THING_UUID);

        restMetricMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetric.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetric))
            )
            .andExpect(status().isOk());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeUpdate);
        Metric testMetric = metricList.get(metricList.size() - 1);
        assertThat(testMetric.getThingUuid()).isEqualTo(UPDATED_THING_UUID);
    }

    @Test
    @Transactional
    void fullUpdateMetricWithPatch() throws Exception {
        // Initialize the database
        metricRepository.saveAndFlush(metric);

        int databaseSizeBeforeUpdate = metricRepository.findAll().size();

        // Update the metric using partial update
        Metric partialUpdatedMetric = new Metric();
        partialUpdatedMetric.setId(metric.getId());

        partialUpdatedMetric.thingUuid(UPDATED_THING_UUID);

        restMetricMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetric.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetric))
            )
            .andExpect(status().isOk());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeUpdate);
        Metric testMetric = metricList.get(metricList.size() - 1);
        assertThat(testMetric.getThingUuid()).isEqualTo(UPDATED_THING_UUID);
    }

    @Test
    @Transactional
    void patchNonExistingMetric() throws Exception {
        int databaseSizeBeforeUpdate = metricRepository.findAll().size();
        metric.setId(count.incrementAndGet());

        // Create the Metric
        MetricDTO metricDTO = metricMapper.toDto(metric);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetricMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metricDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metricDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetric() throws Exception {
        int databaseSizeBeforeUpdate = metricRepository.findAll().size();
        metric.setId(count.incrementAndGet());

        // Create the Metric
        MetricDTO metricDTO = metricMapper.toDto(metric);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metricDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetric() throws Exception {
        int databaseSizeBeforeUpdate = metricRepository.findAll().size();
        metric.setId(count.incrementAndGet());

        // Create the Metric
        MetricDTO metricDTO = metricMapper.toDto(metric);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetricMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(metricDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Metric in the database
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetric() throws Exception {
        // Initialize the database
        metricRepository.saveAndFlush(metric);

        int databaseSizeBeforeDelete = metricRepository.findAll().size();

        // Delete the metric
        restMetricMockMvc
            .perform(delete(ENTITY_API_URL_ID, metric.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Metric> metricList = metricRepository.findAll();
        assertThat(metricList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
