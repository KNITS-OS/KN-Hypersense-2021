package com.knits.smartfactory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.smartfactory.IntegrationTest;
import com.knits.smartfactory.domain.Things;
import com.knits.smartfactory.repository.ThingsRepository;
import com.knits.smartfactory.service.dto.ThingsDTO;
import com.knits.smartfactory.service.mapper.ThingsMapper;
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
 * Integration tests for the {@link ThingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThingsResourceIT {

    private static final String DEFAULT_THING_UUID = "AAAAAAAAAA";
    private static final String UPDATED_THING_UUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/things";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThingsRepository thingsRepository;

    @Autowired
    private ThingsMapper thingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThingsMockMvc;

    private Things things;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Things createEntity(EntityManager em) {
        Things things = new Things().thingUuid(DEFAULT_THING_UUID);
        return things;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Things createUpdatedEntity(EntityManager em) {
        Things things = new Things().thingUuid(UPDATED_THING_UUID);
        return things;
    }

    @BeforeEach
    public void initTest() {
        things = createEntity(em);
    }

    @Test
    @Transactional
    void createThings() throws Exception {
        int databaseSizeBeforeCreate = thingsRepository.findAll().size();
        // Create the Things
        ThingsDTO thingsDTO = thingsMapper.toDto(things);
        restThingsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thingsDTO)))
            .andExpect(status().isCreated());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeCreate + 1);
        Things testThings = thingsList.get(thingsList.size() - 1);
        assertThat(testThings.getThingUuid()).isEqualTo(DEFAULT_THING_UUID);
    }

    @Test
    @Transactional
    void createThingsWithExistingId() throws Exception {
        // Create the Things with an existing ID
        things.setId(1L);
        ThingsDTO thingsDTO = thingsMapper.toDto(things);

        int databaseSizeBeforeCreate = thingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThingsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThings() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);

        // Get all the thingsList
        restThingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(things.getId().intValue())))
            .andExpect(jsonPath("$.[*].thingUuid").value(hasItem(DEFAULT_THING_UUID)));
    }

    @Test
    @Transactional
    void getThings() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);

        // Get the things
        restThingsMockMvc
            .perform(get(ENTITY_API_URL_ID, things.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(things.getId().intValue()))
            .andExpect(jsonPath("$.thingUuid").value(DEFAULT_THING_UUID));
    }

    @Test
    @Transactional
    void getNonExistingThings() throws Exception {
        // Get the things
        restThingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThings() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);

        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();

        // Update the things
        Things updatedThings = thingsRepository.findById(things.getId()).get();
        // Disconnect from session so that the updates on updatedThings are not directly saved in db
        em.detach(updatedThings);
        updatedThings.thingUuid(UPDATED_THING_UUID);
        ThingsDTO thingsDTO = thingsMapper.toDto(updatedThings);

        restThingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
        Things testThings = thingsList.get(thingsList.size() - 1);
        assertThat(testThings.getThingUuid()).isEqualTo(UPDATED_THING_UUID);
    }

    @Test
    @Transactional
    void putNonExistingThings() throws Exception {
        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();
        things.setId(count.incrementAndGet());

        // Create the Things
        ThingsDTO thingsDTO = thingsMapper.toDto(things);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThings() throws Exception {
        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();
        things.setId(count.incrementAndGet());

        // Create the Things
        ThingsDTO thingsDTO = thingsMapper.toDto(things);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThings() throws Exception {
        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();
        things.setId(count.incrementAndGet());

        // Create the Things
        ThingsDTO thingsDTO = thingsMapper.toDto(things);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThingsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thingsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThingsWithPatch() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);

        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();

        // Update the things using partial update
        Things partialUpdatedThings = new Things();
        partialUpdatedThings.setId(things.getId());

        restThingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThings))
            )
            .andExpect(status().isOk());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
        Things testThings = thingsList.get(thingsList.size() - 1);
        assertThat(testThings.getThingUuid()).isEqualTo(DEFAULT_THING_UUID);
    }

    @Test
    @Transactional
    void fullUpdateThingsWithPatch() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);

        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();

        // Update the things using partial update
        Things partialUpdatedThings = new Things();
        partialUpdatedThings.setId(things.getId());

        partialUpdatedThings.thingUuid(UPDATED_THING_UUID);

        restThingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThings))
            )
            .andExpect(status().isOk());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
        Things testThings = thingsList.get(thingsList.size() - 1);
        assertThat(testThings.getThingUuid()).isEqualTo(UPDATED_THING_UUID);
    }

    @Test
    @Transactional
    void patchNonExistingThings() throws Exception {
        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();
        things.setId(count.incrementAndGet());

        // Create the Things
        ThingsDTO thingsDTO = thingsMapper.toDto(things);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThings() throws Exception {
        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();
        things.setId(count.incrementAndGet());

        // Create the Things
        ThingsDTO thingsDTO = thingsMapper.toDto(things);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThings() throws Exception {
        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();
        things.setId(count.incrementAndGet());

        // Create the Things
        ThingsDTO thingsDTO = thingsMapper.toDto(things);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThingsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(thingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThings() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);

        int databaseSizeBeforeDelete = thingsRepository.findAll().size();

        // Delete the things
        restThingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, things.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
