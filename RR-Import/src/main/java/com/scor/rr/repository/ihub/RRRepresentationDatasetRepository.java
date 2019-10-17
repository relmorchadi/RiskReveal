package com.scor.rr.repository.ihub;

import com.scor.rr.domain.entities.ihub.RRRepresentationDataset;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RRRepresentationDataset Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface RRRepresentationDatasetRepository extends JpaRepository<RRRepresentationDataset, Long> {

	RRRepresentationDataset findByProjectId(String projectId);
	
}