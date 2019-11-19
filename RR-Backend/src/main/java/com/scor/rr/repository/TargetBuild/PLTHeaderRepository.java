package com.scor.rr.repository.TargetBuild;

import com.scor.rr.domain.TargetBuild.PLTHeader;
import com.scor.rr.domain.TargetBuild.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PLTHeaderRepository extends JpaRepository<PLTHeader, Integer> {

    @Query("FROM Workspace \n" +
            "WHERE\n" +
            "workspaceId IN ( SELECT workspaceId FROM Project WHERE projectId IN " +
            "( SELECT projectId FROM PLTHeader  WHERE pltHeaderId = :pltHeaderId)" +
            ")")
    Workspace findParentWorkspace(@Param("pltHeaderId") Integer pltHeaderId);

}
