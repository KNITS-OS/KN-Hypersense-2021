package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.BusinessUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BusinessUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {}
