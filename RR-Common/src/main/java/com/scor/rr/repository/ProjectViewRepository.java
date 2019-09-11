package com.scor.rr.repository;

import com.scor.rr.domain.ProjectView;
import com.scor.rr.domain.ProjectViewId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectViewRepository extends JpaRepository<ProjectView, ProjectViewId> {

    List<ProjectView> findByWorkspaceIdAndUwy(String workspaceId, Integer uwy);

    ProjectView findByProjectId(String projectId);

}
