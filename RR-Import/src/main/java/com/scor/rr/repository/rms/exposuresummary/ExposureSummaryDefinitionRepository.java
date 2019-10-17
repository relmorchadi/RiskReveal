package com.scor.rr.repository.rms.exposuresummary;

import com.scor.rr.domain.entities.rms.exposuresummary.ExposureSummaryDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ExposureSummaryDefinition Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface ExposureSummaryDefinitionRepository
		extends JpaRepository<ExposureSummaryDefinition, Long> {

	ExposureSummaryDefinition findByExposureSummaryAlias(String exposureSummaryAlias);

}