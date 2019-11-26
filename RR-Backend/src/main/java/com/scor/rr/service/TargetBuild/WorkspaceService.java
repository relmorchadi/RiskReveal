package com.scor.rr.service.TargetBuild;

import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.dto.TargetBuild.WorkspaceToggleRequest;
import com.scor.rr.domain.dto.TargetBuild.WorkspaceCount;
import com.scor.rr.repository.TargetBuild.WorkspacePoPin.*;
import com.scor.rr.util.OffsetPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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

    @Autowired
    PinnedWorkspaceRepository pinnedWorkspaceRepository;

    @Autowired
    PinnedWorkspaceViewRepository pinnedWorkspaceViewRepository;

    public List<WorkspaceEntity> getFavoriteWorkspaces(String kw, Integer userId, Integer offset, Integer size) {
        return this.favoriteWorkspaceViewRepository.findAllByUserId("%" + kw + "%", userId, new OffsetPageRequest(offset, size))
                .stream()
                .map(favoriteWorkspaceView -> WorkspaceEntity
                        .builder()
                        .workspaceId(favoriteWorkspaceView.getId())
                        .workspaceContextCode(favoriteWorkspaceView.getWorkspaceContextCode())
                        .workspaceName(favoriteWorkspaceView.getWorkspaceName())
                        .workspaceContextCode(favoriteWorkspaceView.getWorkspaceContextCode())
                        .workspaceUwYear(favoriteWorkspaceView.getWorkspaceUwYear())
                        .cedantName(favoriteWorkspaceView.getCedantName())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<WorkspaceEntity> getRecentWorkspaces(String kw, Integer userId, Integer offset, Integer size) {
        return this.recentWorkspaceViewRepository.findAllByUserId("%"+kw+"%", userId, new OffsetPageRequest(offset, size)).stream()
                .map(recentWorkspaceView -> WorkspaceEntity
                        .builder()
                        .workspaceId(recentWorkspaceView.getId())
                        .workspaceContextCode(recentWorkspaceView.getWorkspaceContextCode())
                        .workspaceName(recentWorkspaceView.getWorkspaceName())
                        .workspaceContextCode(recentWorkspaceView.getWorkspaceContextCode())
                        .workspaceUwYear(recentWorkspaceView.getWorkspaceUwYear())
                        .cedantName(recentWorkspaceView.getCedantName())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<WorkspaceEntity> getAssignedWorkspaces(String kw, Integer userId, Integer offset, Integer size) {
        return this.assignedWorkspaceViewRepository.findAllByUserId("%" + kw + "%", userId, new OffsetPageRequest(offset, size)).stream()
                .map(assignedWorkspaceView -> WorkspaceEntity
                        .builder()
                        .workspaceId(assignedWorkspaceView.getId())
                        .workspaceContextCode(assignedWorkspaceView.getWorkspaceContextCode())
                        .workspaceName(assignedWorkspaceView.getWorkspaceName())
                        .workspaceContextCode(assignedWorkspaceView.getWorkspaceContextCode())
                        .workspaceUwYear(assignedWorkspaceView.getWorkspaceUwYear())
                        .cedantName(assignedWorkspaceView.getCedantName())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<WorkspaceEntity> getPinnedWorkspaces(String kw, Integer userId, Integer offset, Integer size) {
        return this.pinnedWorkspaceViewRepository.findAllByUserId("%" + kw + "%", userId, new OffsetPageRequest(offset, size)).stream()
                .map(pinnedWorkspaceView -> WorkspaceEntity
                        .builder()
                        .workspaceId(pinnedWorkspaceView.getId())
                        .workspaceName(pinnedWorkspaceView.getWorkspaceName())
                        .workspaceContextCode(pinnedWorkspaceView.getWorkspaceContextCode())
                        .workspaceUwYear(pinnedWorkspaceView.getWorkspaceUwYear())
                        .cedantName(pinnedWorkspaceView.getCedantName())
                        .build()
                ).collect(Collectors.toList());
    }

    public WorkspaceCount getWSCount(Integer userId) {
        return new WorkspaceCount(
                this.favoriteWorkspaceViewRepository.getFavoriteWSCount(userId),
                this.recentWorkspaceViewRepository.getRecentWSCount(userId),
                this.assignedWorkspaceViewRepository.getAssignedWSCount(userId),
                this.pinnedWorkspaceViewRepository.getPinnedWSCount(userId)
        );
    }

    public ResponseEntity<String> toggleFavoriteWorkspace(WorkspaceToggleRequest request) {
        this.favoriteWorkspaceRepository.toggleFavoriteWorkspace(request.getWorkspaceContextCode(), request.getWorkspaceUwYear(), request.getUserId());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    public ResponseEntity<String> togglePinnedWorkspace(WorkspaceToggleRequest request) {
        this.pinnedWorkspaceRepository.togglePinnedWorkspace(request.getWorkspaceContextCode(), request.getWorkspaceUwYear(), request.getUserId());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
