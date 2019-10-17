package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 30/03/2015.
 */
public class RDMPortfolioCheck extends AbstractRMSConsistencyCheck{

    private final String sql = "select COUNT(1) as NB_ANLS " +
            "from :rdm:.dbo.rdm_analysis an " +
            "inner join ( " +
            "select " +
            "a.NAME, " +
            "a.DESCRIPTION, " +
            "MAX(a.RUNDATE) as LATEST_RUN_DATE, " +
            "COUNT(1) as ANALYSIS_COUNT " +
            "from :rdm:.dbo.rdm_analysis a " +
            "where " +
            "a.TYPE = 102 " +
            "and a.MODE = 2 " +
            "and a.EXPOSURETYPE = 8017 " +
            "and a.GROUPTYPE = 'ANLS' " +
            "and a.ENGINETYPE = 100 " +
            "group by " +
            "a.NAME, " +
            "a.DESCRIPTION " +
            ") av " +
            "on av.NAME = an.NAME " +
            "and av.LATEST_RUN_DATE = an.RUNDATE " +
            "and av.DESCRIPTION = an.DESCRIPTION " +
            "where " +
            "an.NAME = ? " +
            "and an.TYPE = 102 " +
            "and an.MODE = 2 " +
            "and an.EXPOSURETYPE = 8017 " +
            "and an.GROUPTYPE = 'ANLS' " +
            "and an.ENGINETYPE = 100 " +
            "and exists ( " +
            "select 1 " +
            "from :rdm:.dbo.rdm_portstats ps " +
            "where " +
            "ps.ANLSID = an.ID " +
            "and ps.PERSPCODE = 'GU' " +
            ")";

        public RDMPortfolioCheck(String checkName) {
                super(checkName);
        }


        @Override
    public ConsistencyStatus performCheck() {
            if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performCountCheck(doReplacements(sql),
                "at least one analysis for portfolio "+params.getPortfolio()+" exists in rdm "+params.getRdm(),
                "no analysis for portfolio "+params.getPortfolio()+" exist in rdm "+params.getRdm(),
                params.getPortfolio());
    }
}
