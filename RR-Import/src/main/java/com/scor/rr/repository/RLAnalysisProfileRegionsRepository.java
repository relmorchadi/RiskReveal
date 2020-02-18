package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLAnalysisProfileRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RLAnalysisProfileRegionsRepository extends JpaRepository<RLAnalysisProfileRegion, Long> {
    List<RLAnalysisProfileRegion> findByRlAnalysisRlAnalysisId(Long rlAnalysisId);

    @Procedure(procedureName = "dbonew.usp_RiskLinkDeleteRlAnalysisProfileRegionByAnalysis")
    void deleteByAnalysisId(@Param("analysisId") Long analysisId);
}
