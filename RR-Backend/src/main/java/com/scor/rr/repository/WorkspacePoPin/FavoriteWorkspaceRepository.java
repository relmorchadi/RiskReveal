package com.scor.rr.repository.WorkspacePoPin;

import com.scor.rr.domain.entities.WorkspacePoPin.FavoriteWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface FavoriteWorkspaceRepository extends JpaRepository<FavoriteWorkspace, Long> {

    @Procedure("dbo.usp_ToggleFavoriteWorkspace")
    void toggleFavoriteWorkspace(@Param("workspaceContextCode") String workspaceContextCode, @Param("workspaceUwYear") Integer workspaceUwYear, @Param("userId") Long userId);

    Boolean existsByWorkspaceContextCodeAndWorkspaceUwYearAndUserId(String workspaceContextCode, Integer workspaceUwYear, Integer userId);

}
