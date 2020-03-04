package com.scor.rr.repository.WorkspacePoPin;

import com.scor.rr.domain.entities.WorkspacePoPin.AssignedWorkspaceView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssignedWorkspaceViewRepository extends JpaRepository<AssignedWorkspaceView, Long> {

    @Query("from AssignedWorkspaceView aws where" +
            " (aws.cedantName like :kw or aws.workspaceContextCode like :kw or aws.workspaceName like :kw or aws.workspaceUwYear like :kw ) " +
            " and aws.userId = :userId order by aws.createDate desc")
    List<AssignedWorkspaceView> findAllByUserId(@Param("kw") String kw, @Param("userId") Long userId, Pageable page);

    @Transactional
    @Procedure(procedureName = "dbonew.usp_Count_Assigned_Workspace", outputParameterName = "count")
    Integer getAssignedWSCount(@Param("userId") Long userId);

}
