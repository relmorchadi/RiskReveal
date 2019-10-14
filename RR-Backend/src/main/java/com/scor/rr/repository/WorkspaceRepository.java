package com.scor.rr.repository;


import com.scor.rr.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface WorkspaceRepository extends JpaRepository<Workspace, Long>{

    Optional<Workspace> findByWorkspaceContextCodeAndWorkspaceUwYear(String workspaceId, Integer workspaceUwYear);


    @Query("select distinct ws.workspaceUwYear from Workspace ws where ws.workspaceContextCode= :wsId")
    List<Integer> findDistinctYearsByWorkspaceId(@Param("wsId") String workspaceId);

}
