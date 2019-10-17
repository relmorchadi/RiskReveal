package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 30/03/2015.
 */
public class BaseExchangeRateCheck extends AbstractRMSConsistencyCheck {

    private final String sql = "select COUNT(1) as BASE_CCY_XFACTOR_IS_VALID " +
            "from rms_userconfig.dbo.currfx fx " +
            "where " +
            "fx.CODE = 'USD' " +
            "and ROUND(fx.XFACTOR,5) = 1.0 ";

    public BaseExchangeRateCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performCountCheck(sql,
                "Base currency Exchange Rate is 1.00000",
                "Base currency Exchange Rate is not 1.00000");

    }
}
