package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.entities.Dashboard.DashboardWidget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardWidgetRepository extends JpaRepository<DashboardWidget,Long> {

    DashboardWidget findByWidgetId(long id);
}
