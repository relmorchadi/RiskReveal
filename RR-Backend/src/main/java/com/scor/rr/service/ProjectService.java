package com.scor.rr.service;

import com.scor.rr.domain.ContractSearchResult;
import com.scor.rr.domain.Project;
import com.scor.rr.domain.Workspace;
import com.scor.rr.domain.dto.AddProjectRequest;
import com.scor.rr.repository.ContractSearchResultRepository;
import com.scor.rr.repository.ProjectRepository;
import com.scor.rr.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ContractSearchResultRepository contractSearchResultRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public Project addNewProject(AddProjectRequest request) {

        Workspace workspace = workspaceRepository.findOptWorkspaceByWorkspaceId(request.wsId, request.uwYear)
                .orElseGet(() -> getWorkspaceFromContractResultSearch(request.wsId, request.uwYear));

        if(workspace != null)
        {
            Project project = projectRepository.save(request.project);
            projectRepository.addProjectToWorkspace(workspace.getId(),project.getProjectId());
            return project;
        }
        else return null;

    }

    private Workspace getWorkspaceFromContractResultSearch(String wsId, Integer uwYear){
        Workspace workspace = new Workspace();
        Optional<ContractSearchResult> c = contractSearchResultRepository.findByWorkspaceIdAndUwYear(wsId,uwYear);
        if(c.isPresent()) {
            workspace.setCedantName(c.get().getCedantName());
            workspace.setWorkspaceName(c.get().getWorkspaceName());
            workspace.setWorkspaceContextFlag(c.get().getWorkSpaceId());
            workspace.setContractId(c.get().getTreatyid());
            workspace.setWorkspaceId(new Workspace.WorkspaceId(c.get().getWorkSpaceId(),c.get().getUwYear()));
            return  workspaceRepository.save(workspace);
        }
        return null;
    }

    public Project deleteProject(AddProjectRequest request) {
        Workspace workspace = workspaceRepository.findOptWorkspaceByWorkspaceId(request.wsId, request.uwYear).orElse(null);
        if(workspace != null)
        {
            projectRepository.deleteProjectFromWorkspace(workspace.getId(),request.getProject().getProjectId());
            return request.getProject();
        }
        else return null;
    }
}
