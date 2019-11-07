package com.scor.rr.service.TargetBuild;

import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.domain.dto.TargetBuild.FavoriteWorkspaceRequest;
import com.scor.rr.repository.TargetBuild.WorkspacePoPin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkspaceService {

    @Autowired
    AssignedWorkspaceViewRepository assignedWorkspaceViewRepository;

    @Autowired
    FavoriteWorkspaceRepository favoriteWorkspaceRepository;

    @Autowired
    FavoriteWorkspaceViewRepository favoriteWorkspaceViewRepository;

    @Autowired
    RecentWorkspaceViewRepository recentWorkspaceViewRepository;

    @Autowired
    RecentWorkspaceRepository recentWorkspaceRepository;

    public List<Workspace> getFavoriteWorkspaces(Integer userId, Integer offset, Integer size) {
        int page = offset / size;
        return this.favoriteWorkspaceViewRepository.findAllByUserId(userId, PageRequest.of(page, size))
                .stream()
                .map( favoriteWorkspaceView -> Workspace
                        .builder()
                        .workspaceId(favoriteWorkspaceView.getWorkspaceId())
                        .workspaceName(favoriteWorkspaceView.getWorkspaceName())
                        .workspaceContextCode(favoriteWorkspaceView.getWorkspaceContextCode())
                        .workspaceUwYear(favoriteWorkspaceView.getWorkspaceUwYear())
                        .cedantName(favoriteWorkspaceView.getCedantName())
                        .build()
                ).collect(Collectors.toList());
    }

    public ResponseEntity<String> toggleFavoriteWorkspace(FavoriteWorkspaceRequest request) {
        this.favoriteWorkspaceRepository.toggleFavoriteWorkspace(request.getWorkspaceId(), request.getUserId());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    public List<Workspace> getRecentWorkspaces(Integer userId, Integer offset, Integer size) {
        int page = offset / size;
        return this.recentWorkspaceViewRepository.findAllByUserId(userId, PageRequest.of(page, size)).stream()
                .map( recentWorkspaceView -> Workspace
                        .builder()
                        .workspaceId(recentWorkspaceView.getWorkspaceId())
                        .workspaceName(recentWorkspaceView.getWorkspaceName())
                        .workspaceContextCode(recentWorkspaceView.getWorkspaceContextCode())
                        .workspaceUwYear(recentWorkspaceView.getWorkspaceUwYear())
                        .cedantName(recentWorkspaceView.getCedantName())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<Workspace> getAssignedWorkspaces(Integer userId, Integer offset, Integer size) {
        int page = offset / size;
        return this.assignedWorkspaceViewRepository.findAllByUserId(userId, PageRequest.of(page, size)).stream()
                .map( assignedWorkspaceView -> Workspace
                        .builder()
                        .workspaceId(assignedWorkspaceView.getWorkspaceId())
                        .workspaceName(assignedWorkspaceView.getWorkspaceName())
                        .workspaceContextCode(assignedWorkspaceView.getWorkspaceContextCode())
                        .workspaceUwYear(assignedWorkspaceView.getWorkspaceUwYear())
                        .cedantName(assignedWorkspaceView.getCedantName())
                        .build()
                ).collect(Collectors.toList());
    }



}
