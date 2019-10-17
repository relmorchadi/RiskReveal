package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.workflow.ConsistencyStatus;
import org.springframework.dao.DataAccessException;

/**
 * Created by U003263 on 30/03/2015.
 */
public class EDMPortNumCheck extends AbstractRMSConsistencyCheck{

    public EDMPortNumCheck(String checkName) {
		super(checkName);
		}

	private final String sql = "select COUNT(1) as portnum_count"+
    		" from :edm:.dbo.portinfo po"+
    		" where po.PORTNUM = ?";

    @Override
    public ConsistencyStatus performCheck() {
        if (true) return new ConsistencyStatus(ConsistencyStatus.Status.OK, "OK", "OK");

    	ConsistencyStatus.Status status = ConsistencyStatus.Status.OK;
    String message = "pas de portfolio duplique";
    Integer cpt = null;
    try {
        cpt = jdbcTemplate.queryForObject(doReplacements(sql), Integer.class, params.getPortfolio());
    } catch (DataAccessException e) {
        status = ConsistencyStatus.Status.KO;
        message = "query failed: "+e.getMessage();
        log.error("query error ", e);
    }
    if (cpt>1){
        status = ConsistencyStatus.Status.KO;
        message = "portfolio duplique: "+cpt;
    }

    return new ConsistencyStatus(status, message, checkName);


    }

}
