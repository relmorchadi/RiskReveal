package com.scor.rr.repository;

import com.scor.rr.domain.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface WorkspaceEntityRepository extends JpaRepository<WorkspaceEntity, Long> {

    Optional<WorkspaceEntity> findByWorkspaceContextCodeAndWorkspaceUwYear(String workspaceId, Integer workspaceUwYear);

    @Query("FROM WorkspaceEntity ws where ws.workspaceId IN (SELECT pr.workspaceId FROM ProjectEntity pr, PltHeaderEntity plt WHERE plt.pltHeaderId = :pltHeaderId AND plt.projectId = pr.projectId)")
    Optional<WorkspaceEntity> findWorkspaceByPltHeaderId(@Param("pltHeaderId") Long pltHeaderId);

    @Transactional
    @Query(value = "exec dbonew.usp_Count_Expected_Region_Peril @treatyId= :treatyId,@year= :uwYear, @sectionId= :sectionId", nativeQuery = true)
    List<String> findExpectedRegionPeril(@Param("treatyId") String treatyId, @Param("uwYear") int uwYear, @Param("sectionId") int sectionId);

//    @Transactional
//    @Query(value = "exec dbo.count_expectedScope_counter @wsId=:wsId", nativeQuery = true)
//    ExpectedRegionPerilCountersProjection countExpectedScopeCounters(@Param("wsId") int wsId);
}
