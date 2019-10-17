package com.scor.rr.repository.workspace;

import com.scor.rr.domain.entities.workspace.workspaceContext.WorkspaceContext;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Workspace Context Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface WorkspaceContextRepository extends JpaRepository<WorkspaceContext, Long> {

}