package com.scor.rr.service;

import com.scor.rr.domain.Project;
import com.scor.rr.domain.Workspace;
import com.scor.rr.repository.ContractSearchResultRepository;
import com.scor.rr.repository.ProjectRepository;
import com.scor.rr.repository.WorkspaceRepository;
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
                .map(ws -> projectRepository.save(p.builder().workspaceId(ws.getWorkspaceId()).projectId(null).build()))
                .orElseGet(() ->
                        contractSearchResultRepository.findByWorkspaceIdAndUwYear(wsId, uwy)
                                .map(targetContract -> workspaceRepository.save(new Workspace(targetContract)))
                                .map(newWs -> projectRepository.save(p.builder().projectId(null).workspaceId(newWs.getWorkspaceId()).build()))
                                .orElseThrow(() -> new RuntimeException("No available Workspace with ID : " + wsId + "-" + uwy))
                );
    }


    public void deleteProject(Long projectId) {
        if (projectRepository.existsById(projectId))
            projectRepository.deleteById(projectId);
        else
            throw new RuntimeException("No available Project with ID : " + projectId);
    }
}
