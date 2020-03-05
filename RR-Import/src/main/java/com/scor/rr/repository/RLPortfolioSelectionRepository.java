package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLPortfolioSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RLPortfolioSelectionRepository extends JpaRepository<RLPortfolioSelection, Long> {

    @Transactional(transactionManager = "rrTransactionManager")
    void deleteByProjectId(Long projectId);

    @Query("SELECT rlPortfolioSelectionId FROM RLPortfolioSelection WHERE projectId=:projectId")
    List<Long> findRLPortfolioSelectionIdByProjectId(Long projectId);

    List<RLPortfolioSelection> findByProjectId(Long projectId);

    @Procedure("dbonew.uspRiskLinkDeletePortfolioSummary")
    void deleteByPortfolioIdAndProjectId(@Param("rlPortfolioId") Long rlPortfolioId, @Param("projectId") Long projectId);

}
