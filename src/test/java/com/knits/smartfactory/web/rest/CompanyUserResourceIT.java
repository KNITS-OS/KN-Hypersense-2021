package com.knits.smartfactory.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.knits.smartfactory.IntegrationTest;
import com.knits.smartfactory.domain.CompanyUser;
import com.knits.smartfactory.repository.CompanyUserRepository;
import com.knits.smartfactory.service.dto.CompanyUserDTO;
import com.knits.smartfactory.service.mapper.CompanyUserMapper;
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
 * Integration tests for the {@link CompanyUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyUserResourceIT {

    private static final String DEFAULT_USERS_UUID = "AAAAAAAAAA";
    private static final String UPDATED_USERS_UUID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/company-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyUserRepository companyUserRepository;

    @Autowired
    private CompanyUserMapper companyUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyUserMockMvc;

    private CompanyUser companyUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyUser createEntity(EntityManager em) {
        CompanyUser companyUser = new CompanyUser().usersUuid(DEFAULT_USERS_UUID);
        return companyUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyUser createUpdatedEntity(EntityManager em) {
        CompanyUser companyUser = new CompanyUser().usersUuid(UPDATED_USERS_UUID);
        return companyUser;
    }

    @BeforeEach
    public void initTest() {
        companyUser = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyUser() throws Exception {
        int databaseSizeBeforeCreate = companyUserRepository.findAll().size();
        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);
        restCompanyUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyUserDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
        assertThat(testCompanyUser.getUsersUuid()).isEqualTo(DEFAULT_USERS_UUID);
    }

    @Test
    @Transactional
    void createCompanyUserWithExistingId() throws Exception {
        // Create the CompanyUser with an existing ID
        companyUser.setId(1L);
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        int databaseSizeBeforeCreate = companyUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyUserMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanyUsers() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList
        restCompanyUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].usersUuid").value(hasItem(DEFAULT_USERS_UUID)));
    }

    @Test
    @Transactional
    void getCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get the companyUser
        restCompanyUserMockMvc
            .perform(get(ENTITY_API_URL_ID, companyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyUser.getId().intValue()))
            .andExpect(jsonPath("$.usersUuid").value(DEFAULT_USERS_UUID));
    }

    @Test
    @Transactional
    void getNonExistingCompanyUser() throws Exception {
        // Get the companyUser
        restCompanyUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();

        // Update the companyUser
        CompanyUser updatedCompanyUser = companyUserRepository.findById(companyUser.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyUser are not directly saved in db
        em.detach(updatedCompanyUser);
        updatedCompanyUser.usersUuid(UPDATED_USERS_UUID);
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(updatedCompanyUser);

        restCompanyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
        assertThat(testCompanyUser.getUsersUuid()).isEqualTo(UPDATED_USERS_UUID);
    }

    @Test
    @Transactional
    void putNonExistingCompanyUser() throws Exception {
        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();
        companyUser.setId(count.incrementAndGet());

        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyUser() throws Exception {
        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();
        companyUser.setId(count.incrementAndGet());

        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyUser() throws Exception {
        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();
        companyUser.setId(count.incrementAndGet());

        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyUserWithPatch() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();

        // Update the companyUser using partial update
        CompanyUser partialUpdatedCompanyUser = new CompanyUser();
        partialUpdatedCompanyUser.setId(companyUser.getId());

        restCompanyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyUser))
            )
            .andExpect(status().isOk());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
        assertThat(testCompanyUser.getUsersUuid()).isEqualTo(DEFAULT_USERS_UUID);
    }

    @Test
    @Transactional
    void fullUpdateCompanyUserWithPatch() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();

        // Update the companyUser using partial update
        CompanyUser partialUpdatedCompanyUser = new CompanyUser();
        partialUpdatedCompanyUser.setId(companyUser.getId());

        partialUpdatedCompanyUser.usersUuid(UPDATED_USERS_UUID);

        restCompanyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyUser))
            )
            .andExpect(status().isOk());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
        assertThat(testCompanyUser.getUsersUuid()).isEqualTo(UPDATED_USERS_UUID);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyUser() throws Exception {
        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();
        companyUser.setId(count.incrementAndGet());

        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyUser() throws Exception {
        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();
        companyUser.setId(count.incrementAndGet());

        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyUser() throws Exception {
        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();
        companyUser.setId(count.incrementAndGet());

        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        int databaseSizeBeforeDelete = companyUserRepository.findAll().size();

        // Delete the companyUser
        restCompanyUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
