package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.entities.Dashboard.UserDashboardWidget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDashboardWidgetRepository extends JpaRepository<UserDashboardWidget,Long> {
}
