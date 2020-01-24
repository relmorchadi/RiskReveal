package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLPortfolioSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RLPortfolioSelectionRepository extends JpaRepository<RLPortfolioSelection, Long> {

    @Transactional(transactionManager = "rrTransactionManager")
    void deleteByProjectId(Long projectId);

    @Query("SELECT rlPortfolioSelectionId FROM RLPortfolioSelection WHERE projectId=:projectId")
    List<Long> findRLPortfolioSelectionIdByProjectId(Long projectId);

    @Query("SELECT p FROM RLPortfolioSelection p WHERE p.projectId=:projectId")
    List<RLPortfolioSelection> findByProjectId(Long projectId);
}
