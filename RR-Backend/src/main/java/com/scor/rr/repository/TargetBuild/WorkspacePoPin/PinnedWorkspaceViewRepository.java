package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.FavoriteWorkspaceView;
import com.scor.rr.domain.TargetBuild.WorkspacePoPin.PinnedWorkspaceView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PinnedWorkspaceViewRepository extends JpaRepository<PinnedWorkspaceView, Long> {

    @Query("from PinnedWorkspaceView pwsv where " +
            "(pwsv.cedantName like :kw or pwsv.workspaceContextCode like :kw or pwsv.workspaceName like :kw or pwsv.workspaceUwYear like :kw )" +
            " and pwsv.userId = :userId order by pwsv.createdDate desc")
    List<PinnedWorkspaceView> findAllByUserId(@Param("kw") String kw, @Param("userId") Integer userId, Pageable page);

    @Transactional
    @Procedure(procedureName = "dbonew.usp_Count_Pinned_Workspace", outputParameterName = "count")
    Integer getPinnedWSCount(@Param("userId") Integer userId);

}
