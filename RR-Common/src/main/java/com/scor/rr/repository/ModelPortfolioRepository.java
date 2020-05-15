package com.scor.rr.repository;

import com.scor.rr.domain.ModelPortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ModelPortfolioRepository extends JpaRepository<ModelPortfolioEntity, Long> {

    @Query("SELECT distinct port.portfolioName FROM ModelPortfolioEntity port WHERE port.projectId=:projectId")
    List<String> findPortfolioNamesByProjectId(@Param("projectId") Long projectId);

    @Query(value = "SELECT port.ModelPortfolioName, port.Currency, port.DivisionNumber FROM dbo.ModelPortfolio port WHERE port.ProjectId=:projectId", nativeQuery = true)
    List<Map<String, Object>> findPortfolioNamesAndCurrencyAndDivisionByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT distinct port.division FROM ModelPortfolioEntity port WHERE port.projectId=:projectId")
    List<Integer> getDivisionsInProject(Long projectId);
}
