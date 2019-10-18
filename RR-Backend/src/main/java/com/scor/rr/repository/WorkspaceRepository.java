package com.scor.rr.repository;


import com.scor.rr.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface WorkspaceRepository extends JpaRepository<Workspace, Integer>{
    Workspace findWorkspaceByWorkspaceId(Workspace.WorkspaceId id);
    @Query("select w from Workspace w where w.workspaceId.workspaceContextCode= :workspaceId and w.workspaceId.workspaceUwYear= :uwYear")
    Optional<Workspace> findOptWorkspaceByWorkspaceId(String workspaceId, Integer uwYear);
}
