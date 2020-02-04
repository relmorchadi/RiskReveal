package com.scor.rr.exceptions.Dashboard;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class DashboardWidgetNotFoundException extends RRException {
    public DashboardWidgetNotFoundException(long id, String ref) {
        super(ExceptionCodename.DASHBOARD_WIDGET_NOT_FOUND, ref+"Dashboard widget with id: "+ id +" not found");
    }
}
