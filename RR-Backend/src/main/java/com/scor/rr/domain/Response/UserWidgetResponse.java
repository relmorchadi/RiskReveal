package com.scor.rr.domain.Response;

import com.scor.rr.domain.entities.Dashboard.UserDashboardWidget;
import com.scor.rr.domain.entities.Dashboard.UserDashboardWidgetColumns;
import lombok.Data;

import java.util.List;

@Data
public class UserWidgetResponse {

    private UserDashboardWidget userDashboardWidget;
    private List<UserDashboardWidgetColumns> columns;

    public UserWidgetResponse() {
    }

    public UserWidgetResponse(UserDashboardWidget userDashboardWidget, List<UserDashboardWidgetColumns> columns) {
        this.userDashboardWidget = userDashboardWidget;
        this.columns = columns;
    }
}
