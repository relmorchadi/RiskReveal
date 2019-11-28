package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLAnalysisScanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RLAnalysisScanStatusRepository extends JpaRepository<RLAnalysisScanStatus, Integer> {
    @Modifying
    @Transactional(transactionManager = "rrTransactionManager")
    @Query("update RLAnalysisScanStatus ss set ss.scanLevel=1 where ss.rlAnalysisId= :rlModelAnalysisId")
    void updateScanLevelByRlModelAnalysisId(@Param("rlModelAnalysisId") Long rlModelAnalysisId);
}
