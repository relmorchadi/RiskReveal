package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLSourceEpHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RLSourceEpHeaderRepository extends JpaRepository<RLSourceEpHeader, Long> {
    @Transactional(transactionManager = "rrTransactionManager")
    void deleteByRLAnalysisIdAndFinancialPerspective(Long rlAnalysisId, String financialPerspective);

    List<RLSourceEpHeader> findByRLAnalysisId(Long rlAnalysisId);
}
