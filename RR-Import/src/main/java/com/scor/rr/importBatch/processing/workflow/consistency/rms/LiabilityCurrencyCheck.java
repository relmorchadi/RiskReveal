package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 30/03/2015.
 */
public class LiabilityCurrencyCheck extends AbstractRMSConsistencyCheck {

    private final String sql = "select COUNT(1) as LIAB_CURR_CK " +
            "from :edm:.dbo.portinfo p " +
            "inner join :edm:.dbo.portacct pa " +
            "on pa.PORTINFOID = p.PORTINFOID " +
            "inner join :edm:.dbo.accgrp ag " +
            "on ag.ACCGRPID = pa.ACCGRPID " +
            "inner join :edm:.dbo.policy pol " +
            "on pol.ACCGRPID = ag.ACCGRPID " +
            "where " +
            "p.PORTNUM = ? " +
            "and pol.PARTOFCUR = ?";

    public LiabilityCurrencyCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performCountCheck(doReplacements(sql),
                "portfolio " + params.getPortfolio() + " has the right currency " + params.getLIabilityCCY() + " in edm " + params.getEdm(),
                "portfolio " + params.getPortfolio() + " does not have the right currency " + params.getLIabilityCCY() + " in edm " + params.getEdm(),
                params.getPortfolio(), params.getLIabilityCCY());
    }
}
