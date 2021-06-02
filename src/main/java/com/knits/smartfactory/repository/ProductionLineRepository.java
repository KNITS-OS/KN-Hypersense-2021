package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.ProductionLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductionLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionLineRepository extends JpaRepository<ProductionLine, Long> {}
