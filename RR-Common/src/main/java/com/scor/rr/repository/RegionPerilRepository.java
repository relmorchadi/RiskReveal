package com.scor.rr.repository;

import com.scor.rr.domain.RegionPerilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionPerilRepository extends JpaRepository<RegionPerilEntity, Long> {

    RegionPerilEntity findByRegionPerilCode(String rpCode);

    @Query("SELECT regionPerilDesc FROM RegionPerilEntity WHERE regionPerilCode=:regionPerilCode")
    String findDescriptionByCode(@Param("regionPerilCode") String regionPerilCode);
}
