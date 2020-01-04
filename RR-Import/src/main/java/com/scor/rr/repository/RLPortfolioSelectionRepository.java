package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLPortfolioSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RLPortfolioSelectionRepository extends JpaRepository<RLPortfolioSelection, Long> {

    @Transactional(transactionManager = "rrTransactionManager")
    void deleteByProjectId(Long projectId);
}
