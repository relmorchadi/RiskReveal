package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLSourceEpHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RLSourceEpHeaderRepository extends JpaRepository<RLSourceEpHeader, Long> {

    @Transactional(transactionManager = "rrTransactionManager")
    void deleteByRLAnalysisIdAndFinancialPerspective(Long rlAnalysisId, String financialPerspective);

    List<RLSourceEpHeader> findByRLAnalysisId(Long rlAnalysisId);

    @Transactional(transactionManager = "rrTransactionManager")
    @Modifying
    @Query(value = "DELETE FROM RLSourceEpHeader WHERE rLAnalysisId IN (:analysisIds)")
    void deleteByRLAnalysisIdList(List<Long> analysisIds);
}
