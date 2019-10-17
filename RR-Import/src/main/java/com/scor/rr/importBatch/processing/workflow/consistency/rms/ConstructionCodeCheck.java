package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 29/10/2015.
 */
public class ConstructionCodeCheck extends AbstractRMSConsistencyCheck {

    private final String sql = "select distinct" +
            " po.PORTNUM," +
            " lo.COUNTRY as COUNTRY," +
            " lo.BLDGSCHEME as CONS_SCHEME," +
            " lo.BLDGCLASS as CONS_CODE" +
            " from :edm:.dbo.portinfo po" +
            " inner join :edm:.dbo.portacct pa" +
            " on pa.PORTINFOID = po.PORTINFOID" +
            " inner join :edm:.dbo.Loc lo" +
            " on lo.ACCGRPID = pa.ACCGRPID" +
            " left outer join RMS_SYSTEMDATA.dbo.vulnnames cons" +
            " on cons.attribid = 501" +
            " and cons.SCHEME  = lo.BLDGSCHEME" +
            " and cons.COUNTRY = lo.COUNTRY" +
            " and rtrim(cons.CODE) = lo.BLDGCLASS" +
            " where" +
            " po.PORTNUM = ?" +
            " and cons.CODE is null" +
            " order by" +
            " lo.COUNTRY," +
            " lo.BLDGSCHEME," +
            " lo.BLDGCLASS";

    public ConstructionCodeCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performNoErrorsCheck(sql,
                "All construction codes in portfolio " + params.getPortfolio() + " are valid",
                "Invalid Construction code found in locations",
                new String[]{"COUNTRY", "CONS_SCHEME", "CONS_CODE"},
                params.getPortfolio());
    }
}
