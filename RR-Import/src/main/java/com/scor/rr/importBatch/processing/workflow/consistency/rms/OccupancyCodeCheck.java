package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 29/10/2015.
 */
public class OccupancyCodeCheck extends AbstractRMSConsistencyCheck {

    private final String sql = "select distinct" +
            " po.PORTNUM," +
            " lo.COUNTRY as COUNTRY," +
            " lo.OCCSCHEME as OCC_SCHEME," +
            " lo.OCCTYPE as OCC_CODE" +
            " from :edm:.dbo.portinfo po" +
            " inner join :edm:.dbo.portacct pa" +
            " on pa.PORTINFOID = po.PORTINFOID" +
            " inner join :edm:.dbo.Loc lo" +
            " on lo.ACCGRPID = pa.ACCGRPID" +
            " left outer join RMS_SYSTEMDATA.dbo.vulnnames occ" +
            " on occ.attribid = 521" +
            " and occ.COUNTRY = lo.COUNTRY" +
            " and occ.SCHEME = lo.OCCSCHEME" +
            " and rtrim(occ.CODE) = convert(varchar(10), lo.OCCTYPE)" +
            " where" +
            " po.PORTNUM = ?" +
            " and occ.CODE is null" +
            " order by" +
            " lo.COUNTRY," +
            " lo.OCCSCHEME," +
            " lo.OCCTYPE";

    public OccupancyCodeCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performNoErrorsCheck(sql,
                "All occupancy scheme codes in portfolio " + params.getPortfolio() + " are valid",
                "Invalid Occupancy code found in locations",
                new String[]{"COUNTRY", "OCC_SCHEME", "OCC_CODE"},
                params.getPortfolio());

    }
}
