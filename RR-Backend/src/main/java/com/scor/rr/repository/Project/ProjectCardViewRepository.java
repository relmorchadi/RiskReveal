package com.scor.rr.repository.Project;

import com.scor.rr.domain.entities.Project.ProjectCardView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectCardViewRepository extends JpaRepository<ProjectCardView, Long> {
    ProjectCardView findByProjectId(Long projectId);
    List<ProjectCardView> findAllByWorkspaceId(Long workspaceId);

}
