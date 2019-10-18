package com.scor.rr.service;

import com.scor.rr.domain.TargetBuild.Project;
import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.repository.ContractSearchResultRepository;
import com.scor.rr.repository.TargetBuild.ProjectRepository;
import com.scor.rr.repository.TargetBuild.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ContractSearchResultRepository contractSearchResultRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public Project addNewProject(String wsId, Integer uwy, Project p) {
        return workspaceRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(wsId, uwy)
                .map(ws -> projectRepository.save(this.prePersistProject(p, ws.getWorkspaceId())))
                .orElseGet(() ->
                        contractSearchResultRepository.findByWorkspaceIdAndUwYear(wsId, uwy)
                                .map(targetContract -> workspaceRepository.save(new Workspace(targetContract)))
                                .map(newWs -> projectRepository.save(this.prePersistProject(p, newWs.getWorkspaceId())))
                                .orElseThrow(() -> new RuntimeException("No available Workspace with ID : " + wsId + "-" + uwy))
                );
    }

    private Project prePersistProject(Project p, Long wsId) {
        p.setProjectId(null);
        p.setWorkspaceId(wsId);
        return p;
    }

    public Project updateProject(Long projectId, Project project) {
        if (!projectRepository.existsById(projectId))
            throw new RuntimeException("No available Project with ID : " + projectId);
        project.setProjectId(projectId);
        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        if (projectRepository.existsById(projectId))
            projectRepository.deleteById(projectId);
        else
            throw new RuntimeException("No available Project with ID : " + projectId);
    }

}
