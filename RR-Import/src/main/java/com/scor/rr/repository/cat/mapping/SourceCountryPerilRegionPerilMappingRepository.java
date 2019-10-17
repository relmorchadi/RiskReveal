package com.scor.rr.repository.cat.mapping;

import com.scor.rr.domain.entities.references.cat.mapping.SourceCountryPerilRegionPerilMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * SourceCountryPerilRegionPerilMapping Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface SourceCountryPerilRegionPerilMappingRepository
		extends JpaRepository<SourceCountryPerilRegionPerilMapping, String> {

	@Query(value = "select s from SourceCountryPerilRegionPerilMapping s where s.modellingSystem.id = :modellingSystemId and s.sourceCountryCode2 = :sourceCountryCode2 and s.sourcePerilCode = :sourcePerilCode")
	SourceCountryPerilRegionPerilMapping findByModellingSystemIdAndSourceCountryCode2AndSourcePerilCode(
            @Param("modellingSystemId") String modellingSystemId,
            @Param("sourceCountryCode2") String sourceCountryCode2, @Param("sourcePerilCode") String sourcePerilCode);

}