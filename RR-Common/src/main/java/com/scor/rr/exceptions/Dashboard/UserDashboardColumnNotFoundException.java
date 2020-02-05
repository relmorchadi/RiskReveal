package com.scor.rr.exceptions.Dashboard;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class UserDashboardColumnNotFoundException extends RRException {
    public UserDashboardColumnNotFoundException(long id) {
        super(ExceptionCodename.COLUMN_NOT_FOUND, "Column with id: "+ id +" not found");
    }
}
