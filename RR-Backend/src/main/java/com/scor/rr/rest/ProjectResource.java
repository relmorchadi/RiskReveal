package com.scor.rr.rest;

import com.scor.rr.domain.Project;
import com.scor.rr.domain.dto.AddProjectRequest;
import com.scor.rr.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/workspace/projects")
public class ProjectResource {

    @Autowired
    ProjectService projectService;

    @PostMapping()
    public Project addNewProject(@RequestBody AddProjectRequest request) {
        return projectService.addNewProject(request);
    }

    @PostMapping("delete")
    public Project deleteProject(@RequestBody AddProjectRequest request) {
        return projectService.deleteProject(request);
    }

}
