package com.scor.rr.importBatch.processing.batch;

import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.Map;

/**
 * Created by U002629 on 03/04/2015.
 */
public interface ParameterBean extends BaseRMSBean {
    void init();

    Map<String, String> getQueryPlaceHolders();

    Map<String, Object> getQueryParameters();

    Date getRunDate();

    String getPortfolio();

    String getDivision();

    String getCatReqId();

    String getEdm();

    String getRdm();

    String getFpELT();

    String getFpStats();

    String getLIabilityCCY();

    JdbcTemplate getJdbcTemplate();
}
