package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.ProductionLineGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductionLineGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionLineGroupRepository extends JpaRepository<ProductionLineGroup, Long> {}
