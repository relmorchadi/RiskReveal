package com.scor.rr.exceptions.Dashboard;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class WidgetParameterNotFoundException extends RRException {
    public WidgetParameterNotFoundException(long id) {
        super(ExceptionCodename.PARAM_NOT_FOUND, "No parameter found for the widget with id: " + id );
    }
}
