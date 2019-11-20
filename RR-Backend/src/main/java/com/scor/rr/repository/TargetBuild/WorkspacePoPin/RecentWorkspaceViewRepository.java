package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.RecentWorkspaceView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecentWorkspaceViewRepository extends JpaRepository<RecentWorkspaceView, Long> {

    @Query("from RecentWorkspaceView rws where" +
            " (rws.cedantName like :kw or rws.workspaceContextCode like :kw or rws.workspaceName like :kw or rws.workspaceUwYear like :kw ) " +
            " and rws.userId = :userId order by rws.lastOpened desc")
    List<RecentWorkspaceView> findAllByUserId(@Param("kw") String kw, @Param("userId") Integer userId, Pageable page);

    @Transactional
    @Procedure(procedureName = "tb.COUNT_RECENT_Workspace", outputParameterName = "count")
    Integer getRecentWSCount(@Param("userId") Integer userId);

}
