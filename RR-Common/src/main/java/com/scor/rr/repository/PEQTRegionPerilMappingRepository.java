package com.scor.rr.repository;

import com.scor.rr.domain.PEQTRegionPerilMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PEQTRegionPerilMappingRepository extends JpaRepository<PEQTRegionPerilMapping, Long> {

    PEQTRegionPerilMapping findByPeqtRegionPerilCode(String rpCode);
}
