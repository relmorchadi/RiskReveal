package com.scor.rr.domain.Response;

import com.scor.rr.domain.entities.Dashboard.UserDashboard;
import lombok.Data;

import java.util.List;
@Data
public class UserDashboardResponse {

    private UserDashboard userDashboard;
    private List<UserWidgetResponse> widgets;

    public UserDashboardResponse() {
    }

    public UserDashboardResponse(UserDashboard userDashboard, List<UserWidgetResponse> widgets) {
        this.userDashboard = userDashboard;
        this.widgets = widgets;
    }
}
