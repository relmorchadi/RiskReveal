package com.scor.rr.repository.TargetBuild;

import com.scor.rr.domain.TargetBuild.Workspace;
import com.scor.rr.domain.projection.ExpectedRegionPerilCountersProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findByWorkspaceContextCodeAndWorkspaceUwYear(String workspaceId, Integer workspaceUwYear);


    @Query("select distinct ws.workspaceUwYear from Workspace ws where ws.workspaceContextCode= :wsId")
    List<Integer> findDistinctYearsByWorkspaceId(@Param("wsId") String workspaceId);

    @Query("FROM Workspace ws where ws.workspaceId IN (SELECT pr.workspaceId FROM Project pr, PLTHeader plt WHERE plt.pltHeaderId = :pltHeaderId AND plt.projectId = pr.projectId)")
    Optional<Workspace> findWorkspaceByPltHeaderId(@Param("pltHeaderId") Integer pltHeaderId);

    @Transactional
    @Query(value = "exec dbo.count_expected_region_peril @treatyId= :treatyId,@year= :uwYear, @sectionId= :sectionId", nativeQuery = true)
    List<String> countExpectedRegionPeril(@Param("treatyId") String treatyId, @Param("uwYear") int uwYear, @Param("sectionId") int sectionId);

    @Transactional
    @Query(value = "exec dbo.count_expectedScope_counter @wsId=:wsId", nativeQuery = true)
    ExpectedRegionPerilCountersProjection countExpectedScopeCounters(@Param("wsId") int wsId);



}
