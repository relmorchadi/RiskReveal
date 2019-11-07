package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.FavoriteWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface FavoriteWorkspaceRepository extends JpaRepository<FavoriteWorkspace, Long> {

    @Procedure("dr.toggleFavoriteWorkspace")
    void toggleFavoriteWorkspace(@Param("workspaceId") Long workspaceId, @Param("userId") Integer userId);
}
