package com.scor.rr.domain.requests;

import lombok.Data;

@Data
public class DashboardWidgetCreationRequest {

    private long referenceWidgetId;
    private long dashoardId;

    public DashboardWidgetCreationRequest() {
    }

    public DashboardWidgetCreationRequest(long referenceWidgetId, long dashoardId) {
        this.referenceWidgetId = referenceWidgetId;
        this.dashoardId = dashoardId;
    }
}
