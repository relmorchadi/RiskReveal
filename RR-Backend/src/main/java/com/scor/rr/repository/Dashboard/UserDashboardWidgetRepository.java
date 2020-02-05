package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.entities.Dashboard.UserDashboardWidget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDashboardWidgetRepository extends JpaRepository<UserDashboardWidget,Long> {

    UserDashboardWidget findByUserDashboardWidgetId(long id);
    List<UserDashboardWidget> findByUserDashboardId(long id);
    List<UserDashboardWidget> findByUserDashboardIdAndWidgetId(long id,long refId);
}
