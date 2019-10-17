package com.scor.rr.repository.cat.mapping;

import com.scor.rr.domain.entities.references.cat.mapping.ExposureSummaryLookup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ExposureSummaryLookup Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface ExposureSummaryLookupRepository extends JpaRepository<ExposureSummaryLookup, String> {

	ExposureSummaryLookup findByExposureViewTitleAndExposureViewCode(String title, String code);

}