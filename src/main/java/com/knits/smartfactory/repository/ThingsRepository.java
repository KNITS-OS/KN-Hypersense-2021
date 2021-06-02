package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.Things;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Things entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThingsRepository extends JpaRepository<Things, Long> {}
