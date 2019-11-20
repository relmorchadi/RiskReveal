package com.scor.rr.repository.TargetBuild.Project;

import com.scor.rr.domain.TargetBuild.Project.ProjectCardView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectCardViewRepository extends JpaRepository<ProjectCardView, Long> {
    ProjectCardView findByProjectId(Long projectId);
    List<ProjectCardView> findAllByWorkspaceId(Long workspaceId);
}
