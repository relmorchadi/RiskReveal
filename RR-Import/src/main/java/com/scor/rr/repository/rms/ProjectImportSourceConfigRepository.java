package com.scor.rr.repository.rms;

import com.scor.rr.domain.entities.rms.ProjectImportSourceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProjectImportSourceConfig Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface ProjectImportSourceConfigRepository
		extends JpaRepository<ProjectImportSourceConfig, Long> {

	ProjectImportSourceConfig findByProjectIdAndSourceConfigVendor(String projectId, String sourceConfigVendor);

}