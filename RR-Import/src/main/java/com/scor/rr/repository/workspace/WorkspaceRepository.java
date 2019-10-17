package com.scor.rr.repository.workspace;

import com.scor.rr.domain.entities.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Workspace Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface WorkspaceRepository extends JpaRepository<Workspace, String> {

	@Query(value = "select ws from Workspace ws join ws.projects p where p.projectId = :projectId")
	Workspace findByProjectId(@Param("projectId") String projectId);

	@Query(value = "select ws from Workspace ws where ws.workspaceContext.workspaceContextCode = :workspaceContextCode and ws.workspaceContext.workspaceUwYear = :workspaceUwYear")
	Workspace findByWorkspaceCtxCodeAndUwYear(@Param("workspaceContextCode") String workspaceContextCode,
                                              @Param("workspaceUwYear") Integer workspaceUwYear);

}