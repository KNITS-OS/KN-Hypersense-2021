package com.knits.smartfactory.service.impl;

import com.knits.smartfactory.domain.CompanyUser;
import com.knits.smartfactory.repository.CompanyUserRepository;
import com.knits.smartfactory.service.CompanyUserService;
import com.knits.smartfactory.service.dto.CompanyUserDTO;
import com.knits.smartfactory.service.mapper.CompanyUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompanyUser}.
 */
@Service
@Transactional
public class CompanyUserServiceImpl implements CompanyUserService {

    private final Logger log = LoggerFactory.getLogger(CompanyUserServiceImpl.class);

    private final CompanyUserRepository companyUserRepository;

    private final CompanyUserMapper companyUserMapper;

    public CompanyUserServiceImpl(CompanyUserRepository companyUserRepository, CompanyUserMapper companyUserMapper) {
        this.companyUserRepository = companyUserRepository;
        this.companyUserMapper = companyUserMapper;
    }

    @Override
    public CompanyUserDTO save(CompanyUserDTO companyUserDTO) {
        log.debug("Request to save CompanyUser : {}", companyUserDTO);
        CompanyUser companyUser = companyUserMapper.toEntity(companyUserDTO);
        companyUser = companyUserRepository.save(companyUser);
        return companyUserMapper.toDto(companyUser);
    }

    @Override
    public Optional<CompanyUserDTO> partialUpdate(CompanyUserDTO companyUserDTO) {
        log.debug("Request to partially update CompanyUser : {}", companyUserDTO);

        return companyUserRepository
            .findById(companyUserDTO.getId())
            .map(
                existingCompanyUser -> {
                    companyUserMapper.partialUpdate(existingCompanyUser, companyUserDTO);
                    return existingCompanyUser;
                }
            )
            .map(companyUserRepository::save)
            .map(companyUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyUserDTO> findAll() {
        log.debug("Request to get all CompanyUsers");
        return companyUserRepository.findAll().stream().map(companyUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyUserDTO> findOne(Long id) {
        log.debug("Request to get CompanyUser : {}", id);
        return companyUserRepository.findById(id).map(companyUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyUser : {}", id);
        companyUserRepository.deleteById(id);
    }
}
