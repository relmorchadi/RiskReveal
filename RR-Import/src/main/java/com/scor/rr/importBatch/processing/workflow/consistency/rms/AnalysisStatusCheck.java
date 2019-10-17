package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;
import org.springframework.dao.DataAccessException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 30/03/2015.
 */
public class AnalysisStatusCheck extends AbstractRMSConsistencyCheck {

    private final String sql = "select " +
            "an.ID, " +
            "an.NAME, " +
            "an.DESCRIPTION, " +
            "an.STATUS " +
            "from  :rdm:.dbo.rdm_analysis an " +
            "inner join ( " +
            "select " +
            "a.NAME, " +
            "a.DESCRIPTION, " +
            "MAX(a.RUNDATE) as LATEST_RUN_DATE, " +
            "COUNT(1) as ANALYSIS_COUNT " +
            "from  :rdm:.dbo.rdm_analysis a " +
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
            "from  :rdm:.dbo.rdm_portstats ps " +
            "where " +
            "ps.ANLSID = an.ID " +
            "and ps.PERSPCODE = 'GU' " +
            ") " +
            "and an.STATUS <> 102";

    public AnalysisStatusCheck(String checkName) {
        super(checkName);
    }

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");
        ConsistencyStatus.Status status = ConsistencyStatus.Status.OK;
        String message = "All dlm profiles in portfolio "+params.getPortfolio()+" are finished";
        List<Map<String, Object>> rows = null;
        List<String> errors = new LinkedList<String>();
        try {
            rows = jdbcTemplate.queryForList(doReplacements(sql), params.getPortfolio());
            for (Map<String, Object> row : rows) {
                Integer rpStatus = (Integer)row.get("STATUS");
                if(rpStatus!=102)
                    errors.add(row.get("DESCRIPTION")+" : "+row.get("STATUS"));
            }
        } catch (DataAccessException e) {
            status = ConsistencyStatus.Status.KO;
            message = "query failed: " + e.getMessage();
            log.error("query error ", e);
        }
        if (errors.size()!=0) {
            status = ConsistencyStatus.Status.KO;
            StringBuilder sb = new StringBuilder();
            sb.append("The following dlm are not in finished status: ");
            for (String error : errors) {
                sb.append(error).append(" , ");
            }

            message = sb.toString();
        }

        return new ConsistencyStatus(status, message, checkName);
    }
}
