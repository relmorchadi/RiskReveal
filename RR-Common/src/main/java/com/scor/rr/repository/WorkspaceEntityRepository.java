package com.scor.rr.repository;

import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.dto.main.ExpectedRegionPerils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WorkspaceEntityRepository extends JpaRepository<WorkspaceEntity, Long> {

    Optional<WorkspaceEntity> findByWorkspaceContextCodeAndWorkspaceUwYear(String workspaceContextCode, Integer workspaceUwYear);

    WorkspaceEntity findByWorkspaceId(long Id);

    @Query("select distinct w.workspaceUwYear from WorkspaceEntity w where w.workspaceContextCode= :wsId order by w.workspaceUwYear asc")
    List<Integer> findDistinctYearsByWorkspaceContextCode(@Param("wsId") String workspaceContextCode);

    @Query("FROM WorkspaceEntity ws where ws.workspaceId IN (SELECT pr.workspaceId FROM ProjectEntity pr, PltHeaderEntity plt WHERE plt.pltHeaderId = :pltHeaderId AND plt.projectId = pr.projectId)")
    Optional<WorkspaceEntity> findWorkspaceByPltHeaderId(@Param("pltHeaderId") Long pltHeaderId);

    @Transactional
    @Query(value = "exec dbo.usp_DistinctRegionPerilsInScope @WorkspaceContextCode= :workspaceContextCode, @UwYear= :uwYear", nativeQuery = true)
    List<Map<String, Object>> getDistinctRegionPerilsInScopeCount(@Param("workspaceContextCode") String workspaceContextCode, @Param("uwYear") Integer uwYear);

    @Transactional
    @Query(value = "exec dbo.usp_ExposureSummariesForExpectedScope @WorkspaceContextCode= :workspaceContextCode, @UwYear= :uwYear", nativeQuery = true)
    List<Map<String, Object>> getExpectedExposureSummariesCount(@Param("workspaceContextCode") String workspaceContextCode, @Param("uwYear") Integer uwYear);

    @Transactional
    @Query(value = "exec dbo.usp_DistinctRegionPerilsByQualifyingPLTs @WorkspaceContextCode= :workspaceContextCode, @UwYear= :uwYear", nativeQuery = true)
    List<Map<String, Object>> getDistinctRegionPerilsByQualifyingPLTsCount(@Param("workspaceContextCode") String workspaceContextCode, @Param("uwYear") Integer uwYear);

    @Transactional
    @Query(value = "exec dbo.usp_DistinctRPByPublishedToPricingQualifiedPLTs @WorkspaceContextCode= :workspaceContextCode, @UwYear= :uwYear", nativeQuery = true)
    List<Map<String, Object>> getDistinctRPByPublishedToPricingQualifiedPLTsCount(@Param("workspaceContextCode") String workspaceContextCode, @Param("uwYear") Integer uwYear);

    @Transactional
    @Query(value = "exec dbo.usp_DistinctRPByPricedQualifiedPLTs @WorkspaceContextCode= :workspaceContextCode, @UwYear= :uwYear", nativeQuery = true)
    List<Map<String, Object>> getDistinctRPByPricedQualifiedPLTsCount(@Param("workspaceContextCode") String workspaceContextCode, @Param("uwYear") Integer uwYear);

    @Transactional
    @Query(value = "exec dbo.usp_DistinctRPByAccumulatedQualifiedPLTs @WorkspaceContextCode= :workspaceContextCode, @UwYear= :uwYear", nativeQuery = true)
    List<Map<String, Object>> getDistinctRPByAccumulatedQualifiedPLTsCount(@Param("workspaceContextCode") String workspaceContextCode, @Param("uwYear") Integer uwYear);

    @Transactional
    @Query(value = "exec dbo.usp_WorkspaceHeaderStatistics @WorkspaceContextCode= :workspaceContextCode, @UwYear= :uwYear, @userId= :userId", nativeQuery = true)
    List<Map<String, Object>> getWorkspaceHeaderStatistics(@Param("workspaceContextCode") String workspaceContextCode, @Param("uwYear") Integer uwYear, @Param("userId") Long userId);


}
