package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLPortfolioAnalysisRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface RLPortfolioAnalysisRegionRepository extends JpaRepository<RLPortfolioAnalysisRegion, Long> {

    @Transactional(transactionManager = "transactionManager")
    @Modifying
    void deleteByRlPortfolioRlPortfolioId(Long rlPortfolioId);
}
