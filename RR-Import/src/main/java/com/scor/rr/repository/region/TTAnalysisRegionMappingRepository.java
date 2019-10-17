package com.scor.rr.repository.region;

import com.scor.rr.domain.entities.references.cat.mapping.AnalysisRegionMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TTAnalysisRegionMappingRepository extends JpaRepository<AnalysisRegionMapping, String>
{
	AnalysisRegionMapping findByCountryCodeAndAdmin1CodeAndPerilCode(String countryCode, String admin1Code, String perilCode);
	List<AnalysisRegionMapping> findByCountryCodeAndPerilCode(String countryCode, String perilCode);
	AnalysisRegionMapping findByAnalysisRegionCodeAndPerilCode(String analysisRegionCode, String perilCode);
	List<AnalysisRegionMapping> findByCountryCode(String countryCode);
}
