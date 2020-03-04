package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.entities.Dashboard.UserDashboard;
import com.scor.rr.domain.entities.DashboardView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface UserDashboardRepository extends JpaRepository<UserDashboard,Long> {

    List<UserDashboard> findByUserId(long userId);
    void deleteByUserDashboardId(long id);
    UserDashboard findByUserDashboardId(long id);

    @Transactional
    @Modifying
    @Query(value = "exec dbonew.uspDashboardWidgetGetCarsBasedOnStatus @CarStatus=:carStatus," +
            "  @Entity=:entity," +
            " @UserDashboardWidgetId=:userDashboardWidgetId," +
            "@UserCode=:userCode," +
            "@PageNumber=:pageNumber," +
            "@PageSize=:pageSize", nativeQuery = true)
    List<Map<String,Object>> getDataForWidget(@Param("carStatus") String carStatus,
                                              @Param("entity") int entity,
                                              @Param("userDashboardWidgetId") long userDashboardWidgetId,
                                              @Param("userCode") String userCode,
                                              @Param("pageNumber") int pageNumber,
                                              @Param("pageSize") int pageSize);

}
