package com.scor.rr.repository.cat.mapping;

import com.scor.rr.domain.entities.references.cat.mapping.AnalysisRegionMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Analysis Region Mapping Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface AnalysisRegionMappingRepository extends JpaRepository<AnalysisRegionMapping, Long> {

	AnalysisRegionMapping findByAnalysisRegionCodeAndPerilCode(String analysisRegionCode, String perilCode);

}