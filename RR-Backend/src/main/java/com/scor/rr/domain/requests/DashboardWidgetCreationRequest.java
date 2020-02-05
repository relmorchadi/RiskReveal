package com.scor.rr.domain.requests;

import lombok.Data;

@Data
public class DashboardWidgetCreationRequest {

    private long referenceWidgetId;
    private long dashoardId;
    private long userId;

    public DashboardWidgetCreationRequest() {
    }

    public DashboardWidgetCreationRequest(long referenceWidgetId, long dashoardId, long userId) {
        this.referenceWidgetId = referenceWidgetId;
        this.dashoardId = dashoardId;
        this.userId = userId;
    }
}
