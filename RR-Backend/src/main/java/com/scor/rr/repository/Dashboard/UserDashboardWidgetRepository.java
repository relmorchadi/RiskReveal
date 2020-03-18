package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.dto.DashboardChartData;
import com.scor.rr.domain.entities.Dashboard.UserDashboardWidget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserDashboardWidgetRepository extends JpaRepository<UserDashboardWidget,Long> {

    UserDashboardWidget findByUserDashboardWidgetId(long id);
    List<UserDashboardWidget> findByUserDashboardId(long id);
    List<UserDashboardWidget> findByUserDashboardIdAndWidgetId(long id,long refId);


    @Query( value ="select assignedAnalyst,carStatus,  COUNT(carStatus) as numberCarsPerAnalyst from dbo.vw_Dashboard where assignedAnalyst <> ' '   group by  assignedAnalyst,carStatus order by assignedAnalyst", nativeQuery = true)
    List<Map<String,Object>> getDataForChart(@Param("fromm")Date from, @Param("too") Date to);



}
