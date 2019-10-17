package com.scor.rr.repository.references;

import com.scor.rr.domain.entities.references.GeographicResolution;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * GeographicResolution Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface GeographicResolutionRepository extends JpaRepository<GeographicResolution, String> {

	GeographicResolution findByCode(String code);

}