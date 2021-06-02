package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.MetricData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MetricData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetricDataRepository extends JpaRepository<MetricData, Long> {}
