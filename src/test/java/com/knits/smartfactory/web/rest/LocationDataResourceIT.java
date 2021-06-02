package com.knits.smartfactory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.smartfactory.IntegrationTest;
import com.knits.smartfactory.domain.LocationData;
import com.knits.smartfactory.repository.LocationDataRepository;
import com.knits.smartfactory.service.dto.LocationDataDTO;
import com.knits.smartfactory.service.mapper.LocationDataMapper;
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
 * Integration tests for the {@link LocationDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationDataResourceIT {

    private static final String DEFAULT_FLOOR = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR = "BBBBBBBBBB";

    private static final String DEFAULT_ROOM = "AAAAAAAAAA";
    private static final String UPDATED_ROOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/location-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocationDataRepository locationDataRepository;

    @Autowired
    private LocationDataMapper locationDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationDataMockMvc;

    private LocationData locationData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationData createEntity(EntityManager em) {
        LocationData locationData = new LocationData().floor(DEFAULT_FLOOR).room(DEFAULT_ROOM).additionalInfo(DEFAULT_ADDITIONAL_INFO);
        return locationData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationData createUpdatedEntity(EntityManager em) {
        LocationData locationData = new LocationData().floor(UPDATED_FLOOR).room(UPDATED_ROOM).additionalInfo(UPDATED_ADDITIONAL_INFO);
        return locationData;
    }

    @BeforeEach
    public void initTest() {
        locationData = createEntity(em);
    }

    @Test
    @Transactional
    void createLocationData() throws Exception {
        int databaseSizeBeforeCreate = locationDataRepository.findAll().size();
        // Create the LocationData
        LocationDataDTO locationDataDTO = locationDataMapper.toDto(locationData);
        restLocationDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeCreate + 1);
        LocationData testLocationData = locationDataList.get(locationDataList.size() - 1);
        assertThat(testLocationData.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testLocationData.getRoom()).isEqualTo(DEFAULT_ROOM);
        assertThat(testLocationData.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    void createLocationDataWithExistingId() throws Exception {
        // Create the LocationData with an existing ID
        locationData.setId(1L);
        LocationDataDTO locationDataDTO = locationDataMapper.toDto(locationData);

        int databaseSizeBeforeCreate = locationDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationDataMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocationData() throws Exception {
        // Initialize the database
        locationDataRepository.saveAndFlush(locationData);

        // Get all the locationDataList
        restLocationDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationData.getId().intValue())))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].room").value(hasItem(DEFAULT_ROOM)))
            .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO)));
    }

    @Test
    @Transactional
    void getLocationData() throws Exception {
        // Initialize the database
        locationDataRepository.saveAndFlush(locationData);

        // Get the locationData
        restLocationDataMockMvc
            .perform(get(ENTITY_API_URL_ID, locationData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locationData.getId().intValue()))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.room").value(DEFAULT_ROOM))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO));
    }

    @Test
    @Transactional
    void getNonExistingLocationData() throws Exception {
        // Get the locationData
        restLocationDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLocationData() throws Exception {
        // Initialize the database
        locationDataRepository.saveAndFlush(locationData);

        int databaseSizeBeforeUpdate = locationDataRepository.findAll().size();

        // Update the locationData
        LocationData updatedLocationData = locationDataRepository.findById(locationData.getId()).get();
        // Disconnect from session so that the updates on updatedLocationData are not directly saved in db
        em.detach(updatedLocationData);
        updatedLocationData.floor(UPDATED_FLOOR).room(UPDATED_ROOM).additionalInfo(UPDATED_ADDITIONAL_INFO);
        LocationDataDTO locationDataDTO = locationDataMapper.toDto(updatedLocationData);

        restLocationDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeUpdate);
        LocationData testLocationData = locationDataList.get(locationDataList.size() - 1);
        assertThat(testLocationData.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testLocationData.getRoom()).isEqualTo(UPDATED_ROOM);
        assertThat(testLocationData.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    void putNonExistingLocationData() throws Exception {
        int databaseSizeBeforeUpdate = locationDataRepository.findAll().size();
        locationData.setId(count.incrementAndGet());

        // Create the LocationData
        LocationDataDTO locationDataDTO = locationDataMapper.toDto(locationData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocationData() throws Exception {
        int databaseSizeBeforeUpdate = locationDataRepository.findAll().size();
        locationData.setId(count.incrementAndGet());

        // Create the LocationData
        LocationDataDTO locationDataDTO = locationDataMapper.toDto(locationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocationData() throws Exception {
        int databaseSizeBeforeUpdate = locationDataRepository.findAll().size();
        locationData.setId(count.incrementAndGet());

        // Create the LocationData
        LocationDataDTO locationDataDTO = locationDataMapper.toDto(locationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationDataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationDataWithPatch() throws Exception {
        // Initialize the database
        locationDataRepository.saveAndFlush(locationData);

        int databaseSizeBeforeUpdate = locationDataRepository.findAll().size();

        // Update the locationData using partial update
        LocationData partialUpdatedLocationData = new LocationData();
        partialUpdatedLocationData.setId(locationData.getId());

        partialUpdatedLocationData.floor(UPDATED_FLOOR).additionalInfo(UPDATED_ADDITIONAL_INFO);

        restLocationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocationData))
            )
            .andExpect(status().isOk());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeUpdate);
        LocationData testLocationData = locationDataList.get(locationDataList.size() - 1);
        assertThat(testLocationData.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testLocationData.getRoom()).isEqualTo(DEFAULT_ROOM);
        assertThat(testLocationData.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    void fullUpdateLocationDataWithPatch() throws Exception {
        // Initialize the database
        locationDataRepository.saveAndFlush(locationData);

        int databaseSizeBeforeUpdate = locationDataRepository.findAll().size();

        // Update the locationData using partial update
        LocationData partialUpdatedLocationData = new LocationData();
        partialUpdatedLocationData.setId(locationData.getId());

        partialUpdatedLocationData.floor(UPDATED_FLOOR).room(UPDATED_ROOM).additionalInfo(UPDATED_ADDITIONAL_INFO);

        restLocationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocationData))
            )
            .andExpect(status().isOk());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeUpdate);
        LocationData testLocationData = locationDataList.get(locationDataList.size() - 1);
        assertThat(testLocationData.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testLocationData.getRoom()).isEqualTo(UPDATED_ROOM);
        assertThat(testLocationData.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingLocationData() throws Exception {
        int databaseSizeBeforeUpdate = locationDataRepository.findAll().size();
        locationData.setId(count.incrementAndGet());

        // Create the LocationData
        LocationDataDTO locationDataDTO = locationDataMapper.toDto(locationData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocationData() throws Exception {
        int databaseSizeBeforeUpdate = locationDataRepository.findAll().size();
        locationData.setId(count.incrementAndGet());

        // Create the LocationData
        LocationDataDTO locationDataDTO = locationDataMapper.toDto(locationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocationData() throws Exception {
        int databaseSizeBeforeUpdate = locationDataRepository.findAll().size();
        locationData.setId(count.incrementAndGet());

        // Create the LocationData
        LocationDataDTO locationDataDTO = locationDataMapper.toDto(locationData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationData in the database
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocationData() throws Exception {
        // Initialize the database
        locationDataRepository.saveAndFlush(locationData);

        int databaseSizeBeforeDelete = locationDataRepository.findAll().size();

        // Delete the locationData
        restLocationDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, locationData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocationData> locationDataList = locationDataRepository.findAll();
        assertThat(locationDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
