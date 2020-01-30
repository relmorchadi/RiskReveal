package com.scor.rr.domain.Response;

import lombok.Data;

@Data
public class ReferenceWidgetResponse {

    private long widgetId;
    private String widgetName;

    public ReferenceWidgetResponse() {
    }

    public ReferenceWidgetResponse(long widgetId, String widgetName) {
        this.widgetId = widgetId;
        this.widgetName = widgetName;
    }
}
