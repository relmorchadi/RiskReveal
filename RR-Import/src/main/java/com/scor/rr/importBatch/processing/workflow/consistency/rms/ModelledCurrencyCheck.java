package com.scor.rr.importBatch.processing.workflow.consistency.rms;


import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;

public class ModelledCurrencyCheck extends AbstractRMSConsistencyCheck {

    private final String sql = "select " +
            "an.ID, " +
            "an.NAME, " +
            "an.DESCRIPTION, " +
            "an.CURR " +
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
            "and a.STATUS = 102 " +
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
            "and an.STATUS = 102 " +
            "and exists ( " +
            "select 1 " +
            "from :rdm:.dbo.rdm_portstats ps " +
            "where " +
            "ps.ANLSID = an.ID " +
            "and ps.PERSPCODE = 'GU' " +
            ") " +
            "and an.CURR <> ? ";

    public ModelledCurrencyCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        return performNoErrorsCheck(sql,
                "All dlm profiles in portfolio " + params.getPortfolio() + " have the right modelled currency",
                "The following dlm profiled are not modelled in the right currency: ",
                new String[]{"DESCRIPTION", "CURR"},
                params.getPortfolio(), params.getLIabilityCCY());

    }
}
