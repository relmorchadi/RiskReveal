package com.scor.rr.repository.rms.exposuresummary;

import com.scor.rr.domain.entities.rms.exposuresummary.SystemExposureSummaryDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SystemExposureSummaryDefinition Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface SystemExposureSummaryDefinitionRepository
		extends JpaRepository<SystemExposureSummaryDefinition, Long> {

	SystemExposureSummaryDefinition findBySourceVendorAndSourceSystemAndVersionAndExposureSummaryAlias(
            String sourceVendor, String sourceSystem, String version, String exposureSummaryAlias);

}