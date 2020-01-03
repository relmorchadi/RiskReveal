package com.scor.rr.service.state;

import lombok.Data;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@JobScope
@Data
public class FacParameters {

    private static final String RDM = "RDM";
    private static final String EDM = "EDM";
    private static final String PORTFOLIO = "PORTFOLIO";
    private static final String ELT_FP = "ELT_FP";
    private static final String STATS_FP = "STATS_FP";
    private static final String LIABILITY_CCY = "LIABILITY_CCY";
    private static final String CARID = "CARID";

    private Map<String, Object> queryParameters;
    private Map<String, String> queryPlaceHolders;

    @Value("#{jobParameters['catReqId']}")
    private String catReqId;

    private String lob = "01";

    @PostConstruct
    public void init() {

        // TODO : CHANGE IT
        queryParameters.put(LIABILITY_CCY, "USD");
//        CATRequest request = loadRequest();
//        if(request instanceof CATAnalysisRequest) {
//            String currency = ((CATAnalysisRequest)request).getuWanalysis().getDivisions().get(new Integer(division)).getCurrency().getCode();
//            queryParameters.put(LIABILITY_CCY, currency);
//        }
    }

    public String getLiabilityCCY(){
        return (String) queryParameters.get(LIABILITY_CCY);
    }

    protected String getLob(){
        if(lob==null) {
//            final CATRequest one = loadRequest();
//            if (one instanceof CATAnalysisRequest) {
//                CATAnalysisRequest request = (CATAnalysisRequest) one;
//                lob = request.getuWanalysis().getDivisions().get(Integer.parseInt(division)).getLob().getCode();
//            } else
//                lob="";
        }
        return lob;
    }

    public boolean isConstruction(){
        return "02".equals(getLob());
    }
}
