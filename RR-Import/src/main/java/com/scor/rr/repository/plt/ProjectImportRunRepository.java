package com.scor.rr.repository.plt;

import com.scor.rr.domain.entities.plt.ProjectImportRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * ProjectImportRun Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface ProjectImportRunRepository extends JpaRepository<ProjectImportRun, String> {

	@Query("SELECT p FROM ProjectImportRun p WHERE p.project.projectId = :projectId")
	List<ProjectImportRun> findByProjectProjectId(@Param("projectId") String projectId);

	ProjectImportRun findByProjectProjectIdAndRunId(String projectId, Integer runId);

}