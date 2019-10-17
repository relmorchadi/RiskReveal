package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.batch.ParameterBean;
import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
import com.scor.rr.importBatch.processing.workflow.ConsistencyCheck;
import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 30/03/2015.
 */
public abstract class AbstractRMSConsistencyCheck extends BaseRMSBeanImpl implements ConsistencyCheck {
    static final Logger log = LoggerFactory.getLogger(AbstractRMSConsistencyCheck.class);

    final String checkName;
    ParameterBean params;

    public AbstractRMSConsistencyCheck(String checkName) {
        this.checkName = checkName;
    }

    @Override
    public String getCheckName() {
        return checkName;
    }

    public void setRmsParameters(ParameterBean rmsParameters) {
        this.params = rmsParameters;
    }

    String doReplacements(String sql){
        List<String> keys = new LinkedList<String>();
        List<String>values = new LinkedList<String>();
        for (Map.Entry<String, String> placeholder : params.getQueryPlaceHolders().entrySet()) {
            keys.add(placeholder.getKey());
            values.add(placeholder.getValue());
        }
        return StringUtils.replaceEach(sql, keys.toArray(new String[keys.size()]), values.toArray(new String[values.size()]));
    }

    ConsistencyStatus performCountCheck(String sql, String okMessage, String koMessage, Object... params) {
        ConsistencyStatus.Status status = ConsistencyStatus.Status.OK;
        String message = okMessage;
        Integer cpt = null;
        try {
            cpt = jdbcTemplate.queryForObject(sql, Integer.class, params);
        } catch (DataAccessException e) {
            status = ConsistencyStatus.Status.KO;
            message = "query failed: "+e.getMessage();
            log.error("query error ", e);
        }
        if (cpt==null || cpt.equals(0)){
            status = ConsistencyStatus.Status.KO;
            message = koMessage;
        }

        return new ConsistencyStatus(status, message, checkName);
    }

    public ConsistencyStatus performNoErrorsCheck(String sql, String okMessage, String koMessage, String[] errorCols, Object... params) {
        ConsistencyStatus.Status status = ConsistencyStatus.Status.OK;
        StringBuilder sb = new StringBuilder();
        List<Map<String, Object>> rows=null;
        try {
            rows = jdbcTemplate.queryForList(doReplacements(sql), params);
        } catch (DataAccessException e) {
            status = ConsistencyStatus.Status.KO;
            sb.append("query failed: ").append(e.getMessage());
            log.error("query error ", e);
        }

        if (rows != null && rows.size() != 0) {
            status = ConsistencyStatus.Status.KO;
            sb.append(koMessage).append(" [");
            for (Map<String, Object> row : rows) {
                for (String errorCol : errorCols) {
                    sb.append(" : ").append(row.get(errorCol));
                }
                sb.append(" , ");
            }
            sb.append("]");
        } else {
            sb.append(okMessage);
        }

        return new ConsistencyStatus(status, sb.toString(), checkName);
    }

}
