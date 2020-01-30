package com.scor.rr.exceptions.Dashboard;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class DashBoardWidgetTypeNotValidException extends RRException {
    public DashBoardWidgetTypeNotValidException(long id) {
        super(ExceptionCodename.INVALID_WIDGET_TYPE, "Invalid widget type with id: "+ id);
    }
}
