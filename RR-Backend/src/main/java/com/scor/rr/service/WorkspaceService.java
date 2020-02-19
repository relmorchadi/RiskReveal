package com.scor.rr.service;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.dto.TargetBuild.WorkspaceToggleRequest;
import com.scor.rr.domain.dto.TargetBuild.WorkspaceCount;
import com.scor.rr.domain.dto.UserWorkspaceTabsRequest;
import com.scor.rr.domain.entities.UserWorkspaceTabs;
import com.scor.rr.repository.UserWorkspaceTabsRepository;
import com.scor.rr.repository.WorkspacePoPin.*;
import com.scor.rr.util.OffsetPageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
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

    @Autowired
    UserWorkspaceTabsRepository workspaceTabsRepository;

    public List<WorkspaceEntity> getFavoriteWorkspaces(String kw, Integer userId, Integer offset, Integer size) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return this.favoriteWorkspaceViewRepository.findAllByUserId("%" + kw + "%", user.getUserId(), new OffsetPageRequest(offset, size))
                .stream()
                .map(favoriteWorkspaceView -> WorkspaceEntity
                        .builder()
                        .workspaceId(favoriteWorkspaceView.getId())
                        .workspaceContextCode(favoriteWorkspaceView.getWorkspaceContextCode())
                        .workspaceName(favoriteWorkspaceView.getWorkspaceName())
                        .workspaceContextCode(favoriteWorkspaceView.getWorkspaceContextCode())
                        .workspaceUwYear(favoriteWorkspaceView.getWorkspaceUwYear())
                        .clientName(favoriteWorkspaceView.getCedantName())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<WorkspaceEntity> getRecentWorkspaces(String kw, Integer userId, Integer offset, Integer size) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return this.recentWorkspaceViewRepository.findAllByUserId("%"+kw+"%", user.getUserId(), new OffsetPageRequest(offset, size)).stream()
                .map(recentWorkspaceView -> WorkspaceEntity
                        .builder()
                        .workspaceId(recentWorkspaceView.getId())
                        .workspaceContextCode(recentWorkspaceView.getWorkspaceContextCode())
                        .workspaceName(recentWorkspaceView.getWorkspaceName())
                        .workspaceContextCode(recentWorkspaceView.getWorkspaceContextCode())
                        .workspaceUwYear(recentWorkspaceView.getWorkspaceUwYear())
                        .clientName(recentWorkspaceView.getCedantName())
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
                        .clientName(assignedWorkspaceView.getCedantName())
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
                        .clientName(pinnedWorkspaceView.getCedantName())
                        .build()
                ).collect(Collectors.toList());
    }

    public WorkspaceCount getWSCount(Integer userId) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return new WorkspaceCount(
                this.favoriteWorkspaceViewRepository.getFavoriteWSCount(user.getUserId()),
                this.recentWorkspaceViewRepository.getRecentWSCount(user.getUserId()),
                this.assignedWorkspaceViewRepository.getAssignedWSCount(userId),
                this.pinnedWorkspaceViewRepository.getPinnedWSCount(userId)
        );
    }

    public ResponseEntity<String> toggleFavoriteWorkspace(WorkspaceToggleRequest request) {
        UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        this.favoriteWorkspaceRepository.toggleFavoriteWorkspace(request.getWorkspaceContextCode(), request.getWorkspaceUwYear(), user.getUserId());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    public ResponseEntity<String> togglePinnedWorkspace(WorkspaceToggleRequest request) {
        this.pinnedWorkspaceRepository.togglePinnedWorkspace(request.getWorkspaceContextCode(), request.getWorkspaceUwYear(), request.getUserId());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    public ResponseEntity<List<UserWorkspaceTabs>> getTabs(String userCode) {
        try {
            return ResponseEntity.ok(this.workspaceTabsRepository.findAllByUserCodeOrderByOpenedDateDesc(userCode));
        } catch(Exception e) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    public ResponseEntity<?> closeTab(UserWorkspaceTabsRequest request) {
        try {
            this.workspaceTabsRepository.deleteById(request.getUserWorkspaceTabsId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Couldn't DELETE Tab with message: {}", e.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> openTab(UserWorkspaceTabsRequest request) {
        try {
            Optional<UserWorkspaceTabs> opt = this.workspaceTabsRepository.findByUserCodeAndWorkspaceContextCodeAndWorkspaceUwYear(request.getUserCode(), request.getWorkspaceContextCode(), request.getWorkspaceUwYear());

            if(opt.isPresent()) {
                return ResponseEntity.ok(this.workspaceTabsRepository.save(new UserWorkspaceTabs(opt.get().getUserWorkspaceTabsId(), request.getWorkspaceContextCode(), request.getWorkspaceUwYear(), request.getUserCode(), new Date())));
            } else {
                return ResponseEntity.ok(this.workspaceTabsRepository.save(new UserWorkspaceTabs(null, request.getWorkspaceContextCode(), request.getWorkspaceUwYear(), request.getUserCode(), new Date())));
            }

        } catch (Exception e) {
            log.error("Couldn't CREATE Tab with message: {}", e.getMessage());
            return ResponseEntity.ok().build();
        }
    }



}
