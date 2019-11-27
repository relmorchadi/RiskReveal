package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.FavoriteWorkspace;
import com.scor.rr.domain.dto.TargetBuild.WorkspaceCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FavoriteWorkspaceRepository extends JpaRepository<FavoriteWorkspace, Long> {

    @Procedure("toggleFavoriteWorkspace")
    void toggleFavoriteWorkspace(@Param("workspaceContextCode") String workspaceContextCode, @Param("workspaceUwYear") Integer workspaceUwYear, @Param("userId") Integer userId);

    Boolean existsByWorkspaceContextCodeAndWorkspaceUwYearAndUserId(String workspaceContextCode, Integer workspaceUwYear, Integer userId);

}
