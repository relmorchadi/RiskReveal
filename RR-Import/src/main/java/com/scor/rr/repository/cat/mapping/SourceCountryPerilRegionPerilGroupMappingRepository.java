package com.scor.rr.repository.cat.mapping;

import com.scor.rr.domain.entities.references.cat.mapping.SourceCountryPerilRegionPerilGroupMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * SourceCountryPerilRegionPerilGroupMapping Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface SourceCountryPerilRegionPerilGroupMappingRepository
		extends JpaRepository<SourceCountryPerilRegionPerilGroupMapping, String> {

	@Query(value = "select s from SourceCountryPerilRegionPerilGroupMapping s where s.modellingSystem.id = :modellingSystemId and s.sourceCountryCode2 = :sourceCountryCode2 and s.sourcePerilCode = :sourcePerilCode")
	SourceCountryPerilRegionPerilGroupMapping findByModellingSystemIdAndSourceCountryCode2AndSourcePerilCode(
            @Param("modellingSystemId") String modellingSystemId,
            @Param("sourceCountryCode2") String sourceCountryCode2, @Param("sourcePerilCode") String sourcePerilCode);

}