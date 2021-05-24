package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.Factory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Factory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {}
