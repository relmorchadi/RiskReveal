package com.scor.rr.repository.TargetBuild;

import com.scor.rr.domain.TargetBuild.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findByWorkspaceContextCodeAndWorkspaceUwYear(String workspaceId, Integer workspaceUwYear);


    @Query("select distinct ws.workspaceUwYear from Workspace ws where ws.workspaceContextCode= :wsId")
    List<Integer> findDistinctYearsByWorkspaceId(@Param("wsId") String workspaceId);

    @Query("FROM Workspace ws where ws.workspaceId IN (SELECT pr.workspaceId FROM Project pr, PLTHeader plt WHERE plt.pltHeaderId = :pltHeaderId AND plt.projectId = pr.projectId)")
    Optional<Workspace> findWorkspaceByPltHeaderId(@Param("pltHeaderId") Integer pltHeaderId);
}
