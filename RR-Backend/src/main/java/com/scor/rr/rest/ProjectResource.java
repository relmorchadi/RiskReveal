package com.scor.rr.rest;

import com.scor.rr.domain.Project;
import com.scor.rr.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/workspace/projects")
public class ProjectResource {

    @Autowired
    ProjectService projectService;

    @PostMapping()
    public Project addNewProject(@RequestParam String wsId, @RequestParam Integer uwy, @RequestBody Project project) {
        return projectService.addNewProject(wsId, uwy, project);
    }

    @DeleteMapping("delete")
    public void deleteProject(@RequestParam("id") Long projectId) {
        projectService.deleteProject(projectId);
    }

}
