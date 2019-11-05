package com.scor.rr.rest.TargetBuild;

import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.service.TargetBuild.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/workspace")
public class WorkspaceRessource {

    @Autowired
    WorkspaceService workspaceService;

    @GetMapping("favorite")
    List<Workspace> getFavoriteWorkspaces(@Param("userId") Integer userId) {
        return this.workspaceService.getFavoriteWorkspaces(userId);
    }

}
