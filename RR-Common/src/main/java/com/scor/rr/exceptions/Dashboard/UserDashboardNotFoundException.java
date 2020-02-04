package com.scor.rr.exceptions.Dashboard;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class UserDashboardNotFoundException extends RRException {
    public UserDashboardNotFoundException(long id) {
        super(ExceptionCodename.DASHBOARD_NOT_FOUND, "Dashboard with id: " + id + " was not found");
    }
}
