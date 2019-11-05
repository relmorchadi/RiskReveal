package com.scor.rr.service.TargetBuild;

import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.repository.TargetBuild.WorkspacePoPin.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Workspace> getFavoriteWorkspaces(Integer userId) {
        return this.favoriteWorkspaceViewRepository.findAllByUserId(userId)
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

    public List<Workspace> getRecentWorkspaces(Integer userId) {
        return this.recentWorkspaceViewRepository.findAllByUserId(userId)
    }
}
