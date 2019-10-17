package com.scor.rr.repository.cat.mapping;

import com.scor.rr.domain.entities.references.cat.mapping.ModelRAPSourceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ModelRAPSourceMapping Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface ModelRAPSourceMappingRepository extends JpaRepository<ModelRAPSourceMapping, String> {

	@Query(value = "select m from ModelRAPSourceMapping m where m.dlmProfileName = :dlmProfileName and m.modellingSystemVersion.id = :modellingSystemVersionId")
	ModelRAPSourceMapping findByDlmProfileNameIgnoreCaseAndModellingSystemId(
            @Param("dlmProfileName") String dlmProfileName,
            @Param("modellingSystemVersionId") String modellingSystemVersionId);

}