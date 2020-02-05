package com.scor.rr.domain.Response;

import lombok.Data;

@Data
public class ReferenceWidgetResponse {

    private long widgetId;
    private String widgetName;
    private String widgetMode;

    public ReferenceWidgetResponse() {
    }

    public ReferenceWidgetResponse(long widgetId, String widgetName, String widgetMode) {
        this.widgetId = widgetId;
        this.widgetName = widgetName;
        this.widgetMode = widgetMode;
    }
}
