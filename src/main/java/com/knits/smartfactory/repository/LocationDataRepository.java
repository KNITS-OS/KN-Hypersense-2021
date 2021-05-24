package com.knits.smartfactory.repository;

import com.knits.smartfactory.domain.LocationData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LocationData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationDataRepository extends JpaRepository<LocationData, Long> {}
