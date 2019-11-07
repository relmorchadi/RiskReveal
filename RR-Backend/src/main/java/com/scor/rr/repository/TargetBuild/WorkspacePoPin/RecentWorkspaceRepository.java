package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.RecentWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface RecentWorkspaceRepository extends JpaRepository<RecentWorkspace, Long> {

    @Procedure("dr.setRecentWorkspace")
    void setRecentWorkspace(@Param("workspaceId") Long workspaceId, @Param("userId") Integer userId);

}
