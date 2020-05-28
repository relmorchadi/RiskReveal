package com.scor.rr.repository;

import com.scor.rr.domain.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {
    public Optional<ProjectEntity> findByProjectNameAndWorkspaceId(String projectName, Long workspaceId);
}
