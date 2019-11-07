package com.scor.rr.rest.TargetBuild;

import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.domain.dto.TargetBuild.FavoriteWorkspaceRequest;
import com.scor.rr.service.TargetBuild.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/workspace")
public class WorkspaceRessource {

    @Autowired
    WorkspaceService workspaceService;

    @GetMapping("favorite")
    List<Workspace> getFavoriteWorkspaces(@Param("userId") Integer userId, Integer offset, Integer size) {
        return this.workspaceService.getFavoriteWorkspaces(userId, offset, size);
    }

    @PostMapping("favorite")
    ResponseEntity<String> toggleFavoriteWorkspace(@RequestBody FavoriteWorkspaceRequest request) {
        return this.workspaceService.toggleFavoriteWorkspace(request);
    }

    @GetMapping("recent")
    List<Workspace> getRecentWorkspaces(@Param("userId") Integer userId, Integer offset, Integer size) {
        return this.workspaceService.getRecentWorkspaces(userId, offset, size);
    }

    @GetMapping("assigned")
    List<Workspace> getAssignedWorkspaces(@Param("userId") Integer userId, Integer offset, Integer size) {
        return this.workspaceService.getAssignedWorkspaces(userId, offset, size);
    }
}
