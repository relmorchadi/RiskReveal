package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLAnalysisProfileRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RLAnalysisProfileRegionsRepository extends JpaRepository<RLAnalysisProfileRegion, Long> {

    List<RLAnalysisProfileRegion> findByRlAnalysisRlAnalysisId(Long rlAnalysisId);


    @Query(value = "delete from RLAnalysisProfileRegion where RLModelAnalysisId= :analysisId ; select @@ROWCOUNT", nativeQuery = true)
    Integer deleteByAnalysisId(@Param("analysisId") Long analysisId);
}
