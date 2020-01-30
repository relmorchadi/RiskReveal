package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.entities.Dashboard.DashboardWidgetParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DashboardWidgetParameterRepository extends JpaRepository<DashboardWidgetParameter,Long> {
    List<DashboardWidgetParameter> findByDashboardWidgetId(long id);
}
