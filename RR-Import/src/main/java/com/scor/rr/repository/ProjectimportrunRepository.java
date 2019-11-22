package com.scor.rr.repository;

import com.scor.rr.domain.ProjectImportRunEntity;
import com.scor.rr.domain.riskReveal.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectimportrunRepository extends JpaRepository<ProjectImportRunEntity, Long> {
    List<ProjectImportRunEntity> findByProject(Project project);
    List<ProjectImportRunEntity> findByProjectProjectId(Long projectId);
    ProjectImportRunEntity findByProjectProjectIdAndRunId(Long projectId, Integer runId);
}
