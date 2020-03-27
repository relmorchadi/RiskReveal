package com.scor.rr.repository.WorkspacePoPin;

import com.scor.rr.domain.entities.WorkspacePoPin.RecentWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface RecentWorkspaceRepository extends JpaRepository<RecentWorkspace, Long> {

    @Procedure("dbo.usp_ToggleRecentWorkspace")
    void toggleRecentWorkspace(@Param("workspaceContextCode") String workspaceContextCode, @Param("workspaceUwYear") Integer workspaceUwYear,@Param("userId") Long userId);

}
