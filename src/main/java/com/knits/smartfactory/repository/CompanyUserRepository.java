package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.CompanyUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompanyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {}
