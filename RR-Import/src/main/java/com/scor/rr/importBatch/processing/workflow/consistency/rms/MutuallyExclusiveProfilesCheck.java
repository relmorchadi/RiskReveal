package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

/**
 * Created by U002629 on 03/09/2015.
 */
public class MutuallyExclusiveProfilesCheck extends AbstractRMSConsistencyCheck {

    private final String sql = "WITH ModelResults AS" +
            " (" +
            " SELECT  an.ID AS ANALYSIS_ID" +
            " , av.ANALYSIS_COUNT AS ANALYSIS_VERSION" +
            " , an.NAME AS PORTFOLIO_NUM" +
            " , an.EXPOSUREID AS PORTFOLIO_ID" +
            " , an.DESCRIPTION AS DLM_PROFILE_NAME" +
            " , an.CURR AS CURRENCY" +
            " , an.RUNDATE AS ANALYSIS_RUN_DATE" +
            " , an.PERIL AS PERIL" +
            " , an.REGION AS REGION" +
            " , rap.Scope" +
            " , rap.MutuallyExclusiveID" +
            " FROM :rdm:.dbo.rdm_analysis an" +
            " INNER JOIN SCOR_REFERENCE.dbo.RiskAnalysisProfileInfo rap" +
            " ON  rap.ProfileName= an.Description" +
            " AND rap.Scope= 'Account'" +
            " INNER JOIN(" +
            " SELECT  a.NAME" +
            " , a.DESCRIPTION" +
            " , MAX(a.RUNDATE) AS LATEST_RUN_DATE" +
            " , COUNT(1) AS ANALYSIS_COUNT" +
            " FROM :rdm:.dbo.rdm_analysis a" +
            " WHERE a.TYPE = 102 " +
            " AND a.MODE = 2 " +
            " AND a.STATUS = 102 " +
            " AND a.EXPOSURETYPE = 8017 " +
            " AND a.GROUPTYPE = 'ANLS' " +
            " AND a.ENGINETYPE = 100 " +
            " GROUP BY " +
            " a.NAME," +
            " a.DESCRIPTION" +
            " ) av " +
            " ON  av.NAME = an.NAME" +
            " AND av.LATEST_RUN_DATE = an.RUNDATE" +
            " AND av.DESCRIPTION = an.DESCRIPTION" +
            " WHERE an.NAME = ? " +
            " AND an.TYPE = 102 " +
            " AND an.MODE = 2 " +
            " AND an.STATUS = 102 " +
            " AND an.EXPOSURETYPE = 8017 " +
            " AND an.GROUPTYPE = 'ANLS' " +
            " AND an.ENGINETYPE = 100 " +
            " AND EXISTS (" +
            " SELECT 1" +
            " FROM :rdm:.dbo.rdm_portstats ps" +
            " WHERE ps.ANLSID = an.ID" +
            " AND ps.PERSPCODE = 'GU'" +
            " ) " +
            " )" +
            " SELECT   Exception.MutuallyExclusiveID as exID" +
            " , mr.DLM_PROFILE_NAME as RiskAnalysisProfile" +
            " FROM   ModelResults mr" +
            " INNER JOIN (" +
            " SELECT mr.MutuallyExclusiveID, COUNT(1) AS DupCount" +
            " FROM ModelResults mr" +
            " WHERE mr.MutuallyExclusiveID IS NOT NULL" +
            " GROUP BY" +
            " mr.MutuallyExclusiveID" +
            " HAVING COUNT(1) > 1" +
            " )" +
            " AS Exception" +
            " ON Exception.MutuallyExclusiveID = mr.MutuallyExclusiveID";

    public MutuallyExclusiveProfilesCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performNoErrorsCheck(sql,
                "No mutually exclusive profiles in portfolio "+params.getPortfolio(),
                "The following DLM profiles are mutually exclusive: ",
                new String[]{"RiskAnalysisProfile", "exID"},
                params.getPortfolio());

    }
}
