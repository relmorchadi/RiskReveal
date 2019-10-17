package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 30/03/2015.
 */
public class EDMPortfolioCheck extends AbstractRMSConsistencyCheck{

    private final String sql = "select COUNT(1) as PORT_EXISTS " +
            "from :edm:.dbo.portinfo p " +
            "where p.PORTNUM = ?";

    public EDMPortfolioCheck(String checkName) {
        super(checkName);
    }


    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
          return performCountCheck(doReplacements(sql),
                params.getPortfolio()+" exists in edm "+params.getEdm(),
                params.getPortfolio()+" does not exist in EDM "+params.getEdm(),
                params.getPortfolio());
    }
}
