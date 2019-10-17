package com.scor.rr.repository.rms.exposuresummary;

import com.scor.rr.domain.entities.rms.exposuresummary.ExposureSummaryConformerReference;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ExposureSummaryConformerReference Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface ExposureSummaryConformerReferenceRepository
		extends JpaRepository<ExposureSummaryConformerReference, Long> {

	ExposureSummaryConformerReference findBySourceVendorAndSourceSystemAndVersionAndAxisConformerAliasAndInputCode(
            String sourceVendor, String sourceSystem, String version, String axisConformerAlias, String inputCode);

}