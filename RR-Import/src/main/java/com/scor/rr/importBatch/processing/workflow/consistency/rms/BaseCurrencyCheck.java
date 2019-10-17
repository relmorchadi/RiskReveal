package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 30/03/2015.
 */
public class BaseCurrencyCheck extends AbstractRMSConsistencyCheck {

    private final String sql = "select COUNT(1) as BASE_CCY_IS_VALID " +
            "from rms_userconfig.dbo.currbase cb " +
            "where " +
            "cb.BASECURRENCY = 'USD'";

    public BaseCurrencyCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performCountCheck(sql,
                "Base currency is USD",
                "Base currency is not USD");

    }
}
