package com.scor.rr.repository;


import com.scor.rr.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkspaceRepository extends JpaRepository<Workspace, Workspace.WorkspaceId>{
    Workspace findWorkspaceByWorkspaceId(Workspace.WorkspaceId id);
}
