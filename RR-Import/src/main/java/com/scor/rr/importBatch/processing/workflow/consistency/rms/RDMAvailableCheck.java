package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 30/03/2015.
 */
public class RDMAvailableCheck extends AbstractRMSConsistencyCheck {
    private final String sql = "select COUNT(1) as DB_EXISTS " +
            "from sys.databases db " +
            "where " +
            "db.name = ? " +
            "and db.state = 0";

    public RDMAvailableCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performCountCheck(sql,
                params.getRdm()+" exists and it's mounted",
                params.getRdm()+" does not exist or it's not mounted",
                params.getRdm());

    }
}
