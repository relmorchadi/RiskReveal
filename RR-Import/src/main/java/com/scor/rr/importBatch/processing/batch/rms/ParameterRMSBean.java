package com.scor.rr.importBatch.processing.batch.rms;

import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.importBatch.processing.batch.ParameterBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by U002629 on 03/04/2015.
 */
public class ParameterRMSBean extends BaseRMSBeanImpl implements ParameterBean {

    private Map<String, Object> queryParameters;
    private Map<String, String> queryPlaceHolders;

    @Override
    public void init() {
        super.init();
        queryPlaceHolders = new HashMap<String, String>();
        queryPlaceHolders.put(":edm:", edm);
        queryPlaceHolders.put(":rdm:", rdm);

        queryParameters = new HashMap<String, Object>();
        queryParameters.put(CARID, catReqId);
        queryParameters.put(RDM, rdm);
        queryParameters.put(EDM, edm);
        queryParameters.put(PORTFOLIO, portfolio);
        queryParameters.put(ELT_FP, fpELT);
        queryParameters.put(STATS_FP, fpStats);

        CATRequest request = loadRequest();

        // CATAnalysisRequest TO CATRequest
        if(request instanceof CATRequest) {
            String currency = request.getUwAnalysis().getDivisions().get(new Integer(division)).getCurrency().getCode();
            queryParameters.put(LIABILITY_CCY, currency);
        }
    }

    @Override
    public Map<String, String> getQueryPlaceHolders() {
        return queryPlaceHolders;
    }

    @Override
    public Map<String, Object> getQueryParameters() {
        return queryParameters;
    }

    @Override
    public Date getRunDate() {
        return runDate;
    }

    @Override
    public String getPortfolio() {
        return portfolio;
    }

    @Override
    public String getDivision() {
        return division;
    }

    @Override
    public String getCatReqId() {
        return catReqId;
    }

    @Override
    public String getEdm() {
        return edm;
    }

    @Override
    public String getRdm() {
        return rdm;
    }

    @Override
    public String getFpELT() {
        return fpELT;
    }

    @Override
    public String getFpStats() {
        return fpStats;
    }

    @Override
    public String getLIabilityCCY(){
        return (String) queryParameters.get(LIABILITY_CCY);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
