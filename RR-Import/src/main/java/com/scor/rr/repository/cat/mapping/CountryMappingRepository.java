package com.scor.rr.repository.cat.mapping;

import com.scor.rr.domain.entities.references.cat.mapping.CountryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * CountryMapping Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface CountryMappingRepository extends JpaRepository<CountryMapping, String> {

	@Query(value = "select c from CountryMapping c where c.modellingSystem.id = :modellingSystemId and c.sourceISO2A = :sourceISO2A")
	CountryMapping findByModellingSystemIdAndSourceISO2A(@Param("modellingSystemId") String modellingSystemId,
                                                         @Param("sourceISO2A") String sourceISO2A);

	@Query(value = "select c from CountryMapping c where c.modellingSystem.id = :modellingSystemId and c.sourceISO2A = :sourceISO2A and c.sourceISO3A = :sourceISO3A")
	CountryMapping findByModellingSystemIdAndSourceISO2AAndSourceISO3A(
            @Param("modellingSystemId") String modellingSystemId, @Param("sourceISO2A") String sourceISO2A,
            @Param("sourceISO3A") String sourceISO3A);

}