package com.scor.rr.repository;

import com.scor.rr.domain.ProjectImportRun;
import com.scor.rr.domain.riskReveal.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectimportrunRepository extends JpaRepository<ProjectImportRun, Long> {
    List<ProjectImportRun> findByProject(Project project);
    List<ProjectImportRun> findByProjectProjectId(Long projectId);
    ProjectImportRun findByProjectProjectIdAndRunId(Long projectId, Integer runId);
}
