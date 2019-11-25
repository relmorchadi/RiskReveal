package com.scor.rr.repository;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PltHeaderRepository extends JpaRepository<PltHeaderEntity, Long> {
    PltHeaderEntity findByPltHeaderId(Long pkScorPltHeaderId);

    @Query("FROM WorkspaceEntity \n" +
            "WHERE\n" +
            "workspaceId IN ( SELECT workspaceId FROM ProjectEntity WHERE projectId IN " +
            "( SELECT projectId FROM PltHeaderEntity  WHERE pltHeaderId = :pltHeaderId)" +
            ")")
    WorkspaceEntity findParentWorkspace(@Param("pltHeaderId") Integer pltHeaderId);
}

