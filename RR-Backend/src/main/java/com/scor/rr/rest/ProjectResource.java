package com.scor.rr.rest;

import com.scor.rr.domain.ProjectEntity;
import com.scor.rr.domain.dto.TargetBuild.ProjectEditRequest;
import com.scor.rr.domain.dto.TargetBuild.ProjectStatistics;
import com.scor.rr.domain.entities.Project.ProjectCardView;
import com.scor.rr.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/workspace/projects")
public class ProjectResource {

    @Autowired
    ProjectService projectService;

    @PostMapping()
    public ProjectEntity addNewProject(@RequestParam String wsId, @RequestParam Integer uwy, @RequestBody ProjectEntity projectEntity) {
        return projectService.addNewProject(wsId, uwy, projectEntity);
    }

    @PutMapping()
    public ResponseEntity updateProject(@RequestBody ProjectEditRequest request) {
        return projectService.updateProject(request);
    }

    @DeleteMapping("delete")
    public void deleteProject(@RequestParam("id") Long projectId) {
        projectService.deleteProject(projectId);
    }

    @GetMapping("stats")
    public ProjectStatistics getProjetStatistics(@RequestParam("projectId") Long projectId) {
        return this.projectService.getProjetStatistics(projectId);
    }

    @GetMapping()
    public List<ProjectCardView> getProjectsByWorkspace(@RequestParam("workspaceContextCode") String workspaceContextCode,
                                                       @RequestParam("workspaceUwYear") Integer workspaceUwYear) {
        return this.projectService.getAllProjectByWorkspace(workspaceContextCode,workspaceUwYear);
    }
}
