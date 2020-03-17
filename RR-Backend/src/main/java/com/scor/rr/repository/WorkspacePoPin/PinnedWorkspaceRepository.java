package com.scor.rr.repository.WorkspacePoPin;

import com.scor.rr.domain.entities.WorkspacePoPin.PinnedWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface PinnedWorkspaceRepository extends JpaRepository<PinnedWorkspace, Long> {

    @Procedure("dbo.usp_TogglePinnedWorkspace")
    void togglePinnedWorkspace(@Param("workspaceContextCode") String workspaceContextCode, @Param("workspaceUwYear") Integer workspaceUwYear, @Param("userId") Integer userId);
}
