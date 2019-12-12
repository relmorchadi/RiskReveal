package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLAnalysisProfileRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RLAnalysisProfileRegionsRepository extends JpaRepository<RLAnalysisProfileRegion, Long> {
    List<RLAnalysisProfileRegion> findByRLAnalysisRLAnalysisId(Long rlAnalysisId);
}
