package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLPortfolioSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RLPortfolioSelectionRepository extends JpaRepository<RLPortfolioSelection, Long> {

    @Transactional(transactionManager = "theTransactionManager")
    void deleteByProjectId(Long projectId);

    @Query("SELECT rlPortfolioSelectionId FROM RLPortfolioSelection WHERE projectId=:projectId")
    List<Long> findRLPortfolioSelectionIdByProjectId(Long projectId);

    List<RLPortfolioSelection> findByProjectId(Long projectId);

    //@Procedure("dbo.uspRiskLinkDeletePortfolioSummary")
    @Modifying
    @Query("delete from RLPortfolioSelection WHERE rlPortfolio.rlPortfolioId in :ids")
    @Transactional(transactionManager = "theTransactionManager")
    void deleteByPortfolioIdIn(@Param("ids") List<Long> ids);

}
