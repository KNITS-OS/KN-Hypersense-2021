package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.ProductPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductPlanRepository extends JpaRepository<ProductPlan, Long> {}
