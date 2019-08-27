package com.scor.rr.service.cloning;

import com.scor.rr.domain.ProjectEntity;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class CloningProject {

    @Autowired
    ProjectRepository projectRepository;

    public ProjectEntity cloneProject(ProjectEntity projectEntity, WorkspaceEntity workspaceEntity) {
        ProjectEntity p = new ProjectEntity();
        p.setWorkspaceByFkWorkspaceId(workspaceEntity);
        p.setCreationDate(new Timestamp(new Date().getTime()));
        p.setFkCloneSourceProjectId(projectEntity);
        p.setClonedFlag(true);
        p.setDescription(projectEntity.getDescription());
        p.setAssignedTo(projectEntity.getAssignedTo());
        p.setLinkFlag(projectEntity.getLinkFlag());
        p.setClonedFlag(true);
        return projectRepository.save(p);

    }
}
