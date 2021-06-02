package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.ProductionPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductionPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionPlanRepository extends JpaRepository<ProductionPlan, Long> {}
