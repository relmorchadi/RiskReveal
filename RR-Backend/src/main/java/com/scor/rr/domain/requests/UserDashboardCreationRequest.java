package com.scor.rr.domain.requests;

import lombok.Data;

@Data
public class UserDashboardCreationRequest {

    private String dashboardName;

    public UserDashboardCreationRequest() {
    }

    public UserDashboardCreationRequest(long userId, String dashboardName) {
        this.dashboardName = dashboardName;
    }
}
