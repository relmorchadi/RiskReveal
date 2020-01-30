package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.entities.Dashboard.UserDashboardWidgetColumns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDashboardWidgetColumnsRepository extends JpaRepository<UserDashboardWidgetColumns,Long> {

    List<UserDashboardWidgetColumns> findByUserDashboardWidgetId(long id);
}
