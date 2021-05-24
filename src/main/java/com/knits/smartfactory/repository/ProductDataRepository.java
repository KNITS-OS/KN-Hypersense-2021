package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.ProductData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDataRepository extends JpaRepository<ProductData, Long> {}
