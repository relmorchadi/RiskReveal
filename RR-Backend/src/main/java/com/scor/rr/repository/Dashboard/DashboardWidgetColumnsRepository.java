package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.entities.Dashboard.DashboardWidgetColumns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DashboardWidgetColumnsRepository extends JpaRepository<DashboardWidgetColumns,Long> {

    List<DashboardWidgetColumns> findByDashboardWidgetId(long id);
}
