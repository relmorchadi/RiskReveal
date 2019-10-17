package com.scor.rr.repository.cat.mapping;

import com.scor.rr.domain.entities.references.cat.mapping.PerilMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * PerilMapping Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface PerilMappingRepository extends JpaRepository<PerilMapping, String> {

	@Query(value = "select t from PerilMapping t where t.modellingSystem.id = :modellingSystemId and t.sourceValueCode = :sourceValueCode")
	PerilMapping findByModellingSystemIdAndSourceValueCode(@Param("modellingSystem") String modellingSystem,
                                                           @Param("sourceValueCode") String sourceValueCode);

}