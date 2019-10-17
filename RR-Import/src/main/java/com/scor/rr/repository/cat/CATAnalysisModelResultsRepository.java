package com.scor.rr.repository.cat;

import com.scor.rr.domain.entities.cat.CATAnalysisModelResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * CATAnalysisModelResults Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface CATAnalysisModelResultsRepository extends JpaRepository<CATAnalysisModelResults, String> {

	@Query(value = "select camr from CATAnalysisModelResults camr where camr.catAnalysis.catAnalysisId = :catAnalysisId")
	List<CATAnalysisModelResults> findByCATAnalysisId(@Param("catAnalysisId") String catAnalysisId);

}