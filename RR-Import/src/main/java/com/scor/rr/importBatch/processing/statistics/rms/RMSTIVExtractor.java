package com.scor.rr.importBatch.processing.statistics.rms;

import com.scor.rr.domain.entities.cat.CATObjectGroup;
import com.scor.rr.domain.utils.cat.ModellingResult;
import com.scor.rr.importBatch.processing.batch.ParameterBean;
import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
import com.scor.rr.importBatch.processing.domain.ELTData;
import com.scor.rr.importBatch.processing.statistics.StatisticsExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 01/07/2015.
 */
public class RMSTIVExtractor extends BaseRMSBeanImpl implements StatisticsExtractor {

    private static final Logger log = LoggerFactory.getLogger(RMSTIVExtractor.class);

    private ELTData eltData;
    private String tivQuery;
    private ParameterBean rmsParameters;

    public RMSTIVExtractor() {
        super();
    }

    public RMSTIVExtractor(ELTData eltData, String tivQuery, ParameterBean rmsParameters) {
        this.eltData = eltData;
        this.tivQuery = tivQuery;
        this.rmsParameters = rmsParameters;
    }

    public ParameterBean getRmsParameters() {
        return rmsParameters;
    }

    public void setRmsParameters(ParameterBean rmsParameters) {
        this.rmsParameters = rmsParameters;
    }

    public String getTivQuery() {
        return tivQuery;
    }

    public void setTivQuery(String tivQuery) {
        this.tivQuery = tivQuery;
    }

    public ELTData getEltData() {
        return eltData;
    }

    public void setEltData(ELTData eltData) {
        this.eltData = eltData;
    }

    @Override
    public Boolean runExtraction() {
        log.debug("Starting runExtraction");
        CATObjectGroup group = getCatObjectGroup();
        Map<String, ModellingResult> modellingResultsByRegionPeril = group.getModellingResultsByRegionPeril();
        List<Map<String, Object>> tivs;
        if(jobType.equals("PORTFOLIO"))
            tivs = jdbcTemplate.queryForList(tivQuery.replaceAll(":edm:", rmsParameters.getEdm()), rmsParameters.getPortfolio());
        else
            tivs = jdbcTemplate.queryForList(tivQuery.replaceAll(":edm:", rmsParameters.getEdm()), rmsParameters.getLIabilityCCY(), rmsParameters.getPortfolio());

        for (Map<String, Object> tivMap : tivs) {
            final ModellingResult result = modellingResultsByRegionPeril.get(tivMap.get("RegionPerilCode"));
            if (result != null) {
                result.setTivExposureValue((Double) tivMap.get("TotalTIV"));
                result.setLocationCount((Integer) tivMap.get("LocCount"));
            }
        }
        log.debug("runExtraction completed");
        return true;
    }

    @Override
    public Boolean runConformedExtraction() {
        return null;
    }
}
