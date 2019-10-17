package com.scor.rr.repository.cat;

import com.scor.rr.domain.entities.cat.CATAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * CATAnalysis Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface CATAnalysisRepository extends JpaRepository<CATAnalysis, String> {

	@Query(value = "select ca from CATAnalysis ca where ca.catRequest.catRequestId = :catRequestId")
	List<CATAnalysis> findByCATRequestId(@Param("catRequestId") String catRequestId);


}