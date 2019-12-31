package com.scor.rr.repository;

import com.scor.rr.domain.DefaultReturnPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface DefaultReturnPeriodRepository extends JpaRepository<DefaultReturnPeriodEntity, Long> {

    @Transactional
    @Query(value = "exec dbonew.usp_ValidateRP @rp= :rp", nativeQuery = true)
    List<Map<String, Object>> validateRP(@Param("rp") Integer rp);

    @Transactional
    @Query(value = "exec dbonew.usp_FindEpMetricsByWorkspaceAndUserAndCurveType @userId=:userId, @workspaceContextCode=:workspaceContextCode, @uwYear=:uwYear, @type=:curveType", nativeQuery = true)
    List<Map<String, Object>> findEpMetricsByWorkspaceAndUserAndCurveType(@Param("userId") Integer userId, @Param("workspaceContextCode") String workspaceContextCode,@Param("uwYear") Integer uwYear, @Param("curveType") String type);


    @Transactional
    @Query(value = "exec dbonew.usp_FindSingleEpMetricsByWorkspaceAndCurveTypeAndRP @workspaceContextCode=:workspaceContextCode, @uwYear=:uwYear, @curveType=:curveType, @rp=:rp", nativeQuery = true)
    List<Map<String, Object>> findSingleEpMetricsByWorkspaceAndCurveTypeAndRP(@Param("workspaceContextCode") String workspaceContextCode,@Param("uwYear") Integer uwYear, @Param("curveType") String type, @Param("rp") Integer rp);

    @Query("SELECT drp.returnPeriod FROM DefaultReturnPeriodEntity drp WHERE drp.isTableRP = 1 ORDER BY drp.returnPeriod ASC")
    List<Integer> findByIsTableRPOrderByReturnPeriodAsc();
}
