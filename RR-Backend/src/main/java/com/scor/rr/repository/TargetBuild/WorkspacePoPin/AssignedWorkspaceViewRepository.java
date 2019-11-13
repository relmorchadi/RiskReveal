package com.scor.rr.repository.TargetBuild.WorkspacePoPin;

import com.scor.rr.domain.TargetBuild.WorkspacePoPin.AssignedWorkspaceView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssignedWorkspaceViewRepository extends JpaRepository<AssignedWorkspaceView, String> {

    @Query("from AssignedWorkspaceView aws where aws.userId = :userId order by aws.createDate desc")
    List<AssignedWorkspaceView> findAllByUserId(@Param("userId") Integer userId, Pageable page);

    @Transactional
    @Procedure(procedureName = "dr.COUNT_ASSIGNED_Workspace", outputParameterName = "count")
    Integer getAssignedWSCount(@Param("userId") Integer userId);

}
