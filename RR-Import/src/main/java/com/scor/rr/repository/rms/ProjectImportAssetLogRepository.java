package com.scor.rr.repository.rms;

import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProjectImportAssetLog Repository
 *
 * @author HADDINI Zakariyae
 */
public interface ProjectImportAssetLogRepository extends JpaRepository<ProjectImportAssetLog, String> {

    ProjectImportAssetLog findByProjectImportAssetLogIdAndStepId(String projectImportAssetLogId, int StepId);
}