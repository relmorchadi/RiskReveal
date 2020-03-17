package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLImportSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RLImportSelectionRepository extends JpaRepository<RLImportSelection, Long> {

    @Transactional(transactionManager = "rrTransactionManager")
    void deleteByProjectId(Long projectId);

    List<RLImportSelection> findByProjectId(Long projectId);

    @Query("SELECT rlImportSelectionId FROM RLImportSelection WHERE projectId=:projectId")
    List<Long> findRLImportSelectionIdByProjectId(Long projectId);

    @Procedure("dbonew.uspRiskLinkDeleteAnalysisSummary")
    void deleteByRlAnalysisIdAndProjectId(@Param("rlAnalysisId") Long analysisId, @Param("projectId") Long projectId);

}
