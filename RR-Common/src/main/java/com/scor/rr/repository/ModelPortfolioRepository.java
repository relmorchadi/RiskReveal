package com.scor.rr.repository;

import com.scor.rr.domain.ModelPortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModelPortfolioRepository extends JpaRepository<ModelPortfolioEntity, Long> {

    @Query("SELECT distinct port.portfolioName FROM ModelPortfolioEntity port WHERE port.projectId=:projectId")
    List<String> findPortfolioNamesByProjectId(@Param("projectId") Long projectId);
}
