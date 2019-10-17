package com.scor.rr.repository.ihub;

import com.scor.rr.domain.entities.ihub.RRPortfolioStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * RRPortfolioStorage Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface RRPortfolioStorageRepository extends JpaRepository<RRPortfolioStorage, Long> {

	@Query(value = "select ps from RRPortfolioStorage ps where ps.project.projectId = :projectId")
	List<RRPortfolioStorage> findByProjectId(@Param("projectId") String projectId);

	@Query(value = "select ps from RRPortfolioStorage ps where ps.rrPortfolio.rrPortfolioId = :rrPortfolioId")
	List<RRPortfolioStorage> findByRRPortfolioId(@Param("rrPortfolioId") Long rrPortfolioId);

}