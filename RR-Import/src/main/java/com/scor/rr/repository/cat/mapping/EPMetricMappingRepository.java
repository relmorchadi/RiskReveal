package com.scor.rr.repository.cat.mapping;

import com.scor.rr.domain.entities.references.cat.mapping.EPMetricMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * EPMetricMapping Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface EPMetricMappingRepository extends JpaRepository<EPMetricMapping, String> {

	@Query(value = "select t from EPMetricMapping t where t.modellingSystemVersion.id = :modellingSystemVersionId and t.sourceValueCode = :sourceValueCode")
	EPMetricMapping findByModellingSystemVersionIdAndSourceValueCode(
            @Param("modellingSystemVersionId") String modellingSystemVersionId,
            @Param("sourceValueCode") String sourceValueCode);

}