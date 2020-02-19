package com.scor.rr.rest;

import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.dto.TargetBuild.WorkspaceToggleRequest;
import com.scor.rr.domain.dto.TargetBuild.WorkspaceCount;
import com.scor.rr.domain.dto.UserWorkspaceTabsRequest;
import com.scor.rr.domain.dto.WorkspaceDetailsDTO;
import com.scor.rr.domain.entities.UserWorkspaceTabs;
import com.scor.rr.service.SearchService;
import com.scor.rr.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/workspace")
public class WorkspaceResource {

    @Autowired
    WorkspaceService workspaceService;

    @Autowired
    private SearchService searchService;

    @GetMapping
    Object getWorkspaceDetails(@RequestParam("workspaceContextCode") String workspaceId, @RequestParam("workspaceContextUwYear") String uwy, @RequestParam("type") String type){
        return  searchService.getWorkspaceDetails(workspaceId, uwy, type);
    }

    @GetMapping("favorite")
    List<WorkspaceEntity> getFavoriteWorkspaces(@RequestParam(required = false, defaultValue = "") String kw, @RequestParam Integer userId, @RequestParam( defaultValue = "0" ) Integer offset, @RequestParam( defaultValue = "10" ) Integer size) {
        return this.workspaceService.getFavoriteWorkspaces(kw, userId, offset, size);
    }

    @GetMapping("recent")
    List<WorkspaceEntity> getRecentWorkspaces(@RequestParam(required = false, defaultValue = "") String kw, @RequestParam Integer userId, @RequestParam Integer offset, @RequestParam Integer size) {
        return this.workspaceService.getRecentWorkspaces(kw,userId, offset, size);
    }

    @GetMapping("assigned")
    List<WorkspaceEntity> getAssignedWorkspaces(@RequestParam(required = false, defaultValue = "") String kw, @RequestParam Integer userId, @RequestParam Integer offset, @RequestParam Integer size) {
        return this.workspaceService.getAssignedWorkspaces(kw,userId, offset, size);
    }

    @GetMapping("pinned")
    List<WorkspaceEntity> getPinnedWorkspaces(@RequestParam(required = false, defaultValue = "") String kw, @RequestParam Integer userId, @RequestParam Integer offset, @RequestParam Integer size) {
        return this.workspaceService.getPinnedWorkspaces(kw,userId, offset, size);
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

    @GetMapping("tabs/{userCode}")
    ResponseEntity<List<UserWorkspaceTabs>> getTabs(@PathVariable String userCode) { return this.workspaceService.getTabs(userCode);}

    @PostMapping("tabs/close")
    ResponseEntity<?> closeTab(@RequestBody UserWorkspaceTabsRequest request) { return this.workspaceService.closeTab(request);}

    @PostMapping("tabs/open")
    ResponseEntity<?> openTab(@RequestBody UserWorkspaceTabsRequest request) { return this.workspaceService.openTab(request);}

}
