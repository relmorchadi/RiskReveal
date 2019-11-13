package com.scor.rr.rest.TargetBuild;

import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.domain.dto.TargetBuild.WorkspaceToggleRequest;
import com.scor.rr.domain.dto.TargetBuild.WorkspaceCount;
import com.scor.rr.service.TargetBuild.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/workspace")
public class WorkspaceRessource {

    @Autowired
    WorkspaceService workspaceService;

    @GetMapping("favorite")
    List<Workspace> getFavoriteWorkspaces(@RequestParam Integer userId, @RequestParam( defaultValue = "0" ) Integer offset, @RequestParam( defaultValue = "10" ) Integer size) {
        return this.workspaceService.getFavoriteWorkspaces(userId, offset, size);
    }

    @GetMapping("recent")
    List<Workspace> getRecentWorkspaces(@RequestParam Integer userId, @RequestParam Integer offset, @RequestParam Integer size) {
        return this.workspaceService.getRecentWorkspaces(userId, offset, size);
    }

    @GetMapping("assigned")
    List<Workspace> getAssignedWorkspaces(@RequestParam Integer userId, @RequestParam Integer offset, @RequestParam Integer size) {
        return this.workspaceService.getAssignedWorkspaces(userId, offset, size);
    }

    @GetMapping("pinned")
    List<Workspace> getPinnedWorkspaces(@RequestParam Integer userId, @RequestParam Integer offset, @RequestParam Integer size) {
        return this.workspaceService.getPinnedWorkspaces(userId, offset, size);
    }

    @GetMapping("count")
    WorkspaceCount getWSCount(@RequestParam Integer userId) {
        return this.workspaceService.getWSCount(userId);
    }

    @PostMapping("favorite")
    ResponseEntity<String> toggleFavoriteWorkspace(@RequestBody WorkspaceToggleRequest request) {
        return this.workspaceService.toggleFavoriteWorkspace(request);
    }

    @PostMapping("pinned")
    ResponseEntity<String> togglePinnedWorkspace(@RequestBody WorkspaceToggleRequest request) {
        return this.workspaceService.togglePinnedWorkspace(request);
    }

}
