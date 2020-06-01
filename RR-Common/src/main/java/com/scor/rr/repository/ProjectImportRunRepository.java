package com.scor.rr.repository;

import com.scor.rr.domain.ProjectImportRunEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectImportRunRepository extends JpaRepository<ProjectImportRunEntity, Long> {

    List<ProjectImportRunEntity> findByProjectId(Long projectId);

    @Query("FROM ProjectImportRunEntity WHERE projectId =:projectId ORDER BY startDate")
    List<ProjectImportRunEntity> findByProjectIdOrderedByStartDate(@Param("projectId") Long projectId);

    ProjectImportRunEntity findByProjectIdAndRunId(Long projectId, Integer runId);

    ProjectImportRunEntity findFirstByProjectIdOrderByRunId(Long projectId);
}

