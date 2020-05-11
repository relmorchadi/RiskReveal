package com.scor.rr.repository;

import com.scor.rr.domain.DefaultAdjustmentRegionPerilEntity;
import com.scor.rr.domain.DefaultAdjustmentRegionPerilEntityPK;
import com.scor.rr.domain.RegionPerilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultAdjustmentRegionPerilRepository extends JpaRepository<DefaultAdjustmentRegionPerilEntity, DefaultAdjustmentRegionPerilEntityPK> {
    boolean existsByFkDefaultAdjustmentIdAndFkRegionPerilIdAndIncludedExcluded(Integer fkDefaultAdjustment, Long regionPeril, String includedExcluded);
}
