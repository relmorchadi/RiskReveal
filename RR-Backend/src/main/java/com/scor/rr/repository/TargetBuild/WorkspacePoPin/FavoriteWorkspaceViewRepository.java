package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.FavoriteWorkspaceView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavoriteWorkspaceViewRepository extends JpaRepository<FavoriteWorkspaceView, Long> {

    @Query("from FavoriteWorkspaceView fwsv where" +
            " (fwsv.cedantName like :kw or fwsv.workspaceContextCode like :kw or fwsv.workspaceName like :kw or fwsv.workspaceUwYear like :kw ) " +
            " and fwsv.userId = :userId order by fwsv.createdDate desc")
    List<FavoriteWorkspaceView> findAllByUserId(@Param("kw") String kw, @Param("userId") Integer userId, Pageable page);

    @Transactional
    @Procedure(procedureName = "dbonew.usp_Count_Favorite_Workspace", outputParameterName = "count")
    Integer getFavoriteWSCount(@Param("userId") Integer userId);

}
