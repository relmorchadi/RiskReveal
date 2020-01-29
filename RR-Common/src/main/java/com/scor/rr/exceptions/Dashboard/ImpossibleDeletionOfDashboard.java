package com.scor.rr.exceptions.Dashboard;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class ImpossibleDeletionOfDashboard extends RRException {
    public ImpossibleDeletionOfDashboard() {
        super(ExceptionCodename.DASHBOARD_DELETE_IMPOSSIBLE, "Impossible delete of the last dashboard");
    }
}
