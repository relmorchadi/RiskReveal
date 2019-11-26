package com.scor.rr.repository;

import com.scor.rr.domain.ProjectImportRunEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectImportRunRepository extends JpaRepository<ProjectImportRunEntity, Integer> {

    List<ProjectImportRunEntity> findByProjectId(Long projectId);
    ProjectImportRunEntity findByProjectIdAndRunId(Long projectId, Integer runId);
}

