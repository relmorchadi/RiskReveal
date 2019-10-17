package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 30/03/2015.
 */
public class RequestIdCheck extends AbstractRMSConsistencyCheck {

    private final String sql = "select COUNT(1) as REQ_ID_EXISTS " +
            "from :edm:.dbo.portinfo p " +
            "inner join :edm:.dbo.portacct pa " +
            "on pa.PORTINFOID = p.PORTINFOID " +
            "inner join :edm:.dbo.accgrp ag " +
            "on ag.ACCGRPID = pa.ACCGRPID " +
            "where " +
            "p.PORTNUM = ? " +
            "and ag.USERID4 = ?";

    public RequestIdCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performCountCheck(doReplacements(sql),
                params.getCatReqId()+" is referenced in EDM "+params.getEdm()+" for portfolio "+params.getPortfolio(),
                params.getCatReqId()+" is not referenced in EDM "+params.getEdm()+" for portfolio "+params.getPortfolio(),
                params.getPortfolio(), params.getCatReqId());
    }
}
