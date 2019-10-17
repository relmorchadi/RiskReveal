package com.scor.rr.importBatch.processing.elt;

import com.codahale.metrics.annotation.Timed;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RmsAnalysisELT;
import com.scor.rr.domain.enums.KPIConstants;
import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
import com.scor.rr.importBatch.processing.domain.ELTData;
import com.scor.rr.importBatch.processing.domain.ELTDataImpl;
import com.scor.rr.importBatch.processing.domain.ELTLoss;
import com.scor.rr.importBatch.processing.domain.loss.EventLoss;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.repository.tracking.ProjectImportLogRepository;
import com.scor.rr.utils.Step;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 20/02/2015.
 */
public class RiskLinkELTExtractor extends BaseRMSBeanImpl implements ELTExtractor {
    private static final Logger log = LoggerFactory.getLogger(RiskLinkELTExtractor.class);

    @Autowired
    private TransformationPackage transformationPackage;
    @Autowired
    private ProjectImportLogRepository projectImportLogRepository;
    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;
    private ELTData eltData;
    private String eltQuery;

    public RiskLinkELTExtractor() {
        super();
    }

    ///////////// FAC //////////////

    @Override
    public Boolean batchExtract() {
        log.debug("Starting batchExtract");
        extractELTTT(new Integer(0));
        log.debug("batchExtract completed");
        return true;
    }

    public void extractELTTT(Integer threshold) {
        Date startDate = new Date();
        Map<MultiKey, RmsAnalysisELT> extractedELT = new HashMap<>();
        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 4 EXTRACT_ELT for this analysis in loop of many analysis
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            projectImportAssetLogA.setProjectId(transformationPackage.getProjectId());
            projectImportAssetLogA.setStepId(4);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            String anlsId = bundle.getRmsAnalysis().getAnalysisId();
            Long rdmId = bundle.getRmsAnalysis().getRdmId();
            String rdmName = bundle.getRmsAnalysis().getRdmName();
            String fpCode = bundle.getFinancialPerspective().getCode(); // not understood ???

            boolean isTY = StringUtils.equalsIgnoreCase(fpCode, "TY");
            Integer treatyLabelID = isTY ? bundle.getFinancialPerspective().getTreatyId() : null;

            log.info("Extracting ELT data for rdm {} - {}, analysis {}, fp {} ", rdmId, rdmName, anlsId, fpCode);
            String instanceId;
            if (bundle.getRmsAnalysis().getRmsModelDatasource() != null && bundle.getRmsAnalysis().getRmsModelDatasource().getInstanceId() != null) {
                instanceId = bundle.getRmsAnalysis().getRmsModelDatasource().getInstanceId();
            } else {
                log.warn("RmsModelDatasource is null for rmsAnalysis {} - use instanceId from batch", bundle.getRmsAnalysis().getRmsAnalysisId());
                instanceId = getInstanceId();
            }
            RmsAnalysisELT rmsAnalysisELT = extractedELT.get(new MultiKey(instanceId, rdmId, anlsId, fpCode, treatyLabelID));
            if (rmsAnalysisELT == null) {
                rmsAnalysisELT = rmsDataProvider.extractAnalysisELT(instanceId, rdmId, rdmName, anlsId, fpCode, treatyLabelID);
                extractedELT.put(new MultiKey(instanceId, rdmId, anlsId, fpCode, treatyLabelID), rmsAnalysisELT);
            }
            log.debug("Rms Analysis ELT loss data size = {}", rmsAnalysisELT.getEltLosses().size());

            bundle.setRmsAnalysisELT(rmsAnalysisELT);
            bundle.setMinLayerAtt(Double.valueOf(threshold));

            // finish step 4 EXTRACT_ELT for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 4 : EXTRACT_ELT for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
    }

    public ELTData getEltData() {
        return eltData;
    }

    public void setEltData(ELTData eltData) {
        this.eltData = eltData;
    }

    public void setEltQuery(String eltQuery) {
        this.eltQuery = eltQuery;
    }

    @Timed(name = "ELTExtract")
    public List<ELTDataImpl> extractEvents(String ptfName, String financialPerspective, Integer threshold) { // TODO financialPerspective, anlsID, ptfName is selectable from UI
        for (String regionPeril : eltData.getRegionPerils()) {
            final Map<String, Object> kpInfo = getKPInfo();
            kpInfo.put("regionPeril", regionPeril);
            businessKpiService.saveStart(KPIConstants.RR_18_ELT_Extraction.toString(), correlationId, kpInfo);

            log.info("begin extracting ELT for " + regionPeril);
            ELTLoss data = eltData.getLossDataForRp(regionPeril);
            data.setFinancialPerspective(financialPerspective);
            Integer anlsID = data.getAnalysisId();
            data.getEventLosses().addAll(jdbcTemplate.query(new SimplePreparedStatementCreator(eltQuery.replaceAll(":rdm:", rdm)),
                    new ArgumentPreparedStatementSetter(new Object[]{anlsID, financialPerspective, threshold}), new RowMapperResultSetExtractor<>(new RMSEventLossRowMapper())));
            addMessage("ELT EXTRACT", "ELT data extracted OK for " + regionPeril);
            businessKpiService.saveHit(KPIConstants.RR_20_Number_Lines_Extraction.toString(), (double) data.getEventLosses().size(), kpInfo);
            businessKpiService.saveEnd(KPIConstants.RR_18_ELT_Extraction.toString(), correlationId, KPIConstants.STATUS_OK.toString(), kpInfo);
        }
        return null;
    }

    @Timed(name = "ELTExtract")
    public List<ELTDataImpl> extractELTs(String ptfName, String financialPerspective, Integer threshold) { // TODO financialPerspective, anlsID, ptfName is selectable from UI
        for (String regionPeril : eltData.getRegionPerils()) {
            log.info("begin extracting ELT for " + regionPeril);
            ELTLoss data = eltData.getLossDataForRp(regionPeril);
            data.setFinancialPerspective(financialPerspective);
            Integer anlsID = data.getAnalysisId();

            String sqlCommand = "exec SCOR_REFERENCE_DEV.dbo.RR_RL_GetAnalysisElt @rdm_id=%1$d, @rdm_name=%2$s, @analysis_id=%3$d, @fin_persp_code=%4$s";
            sqlCommand = String.format(sqlCommand, rdmId, rdm, anlsID, financialPerspective);

            List<EventLoss> eventLosses = jdbcTemplate.query(sqlCommand, new ELTMapper(threshold));
            data.getEventLosses().addAll(eventLosses);
        }
        return null;
    }

    /**
     * Simple adapter for PreparedStatementCreator, allowing to use a plain SQL statement.
     */
    private static class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {

        private final String sql;

        public SimplePreparedStatementCreator(String sql) {
            Assert.notNull(sql, "SQL must not be null");
            this.sql = sql;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            final PreparedStatement stmt = con.prepareStatement(this.sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            return stmt;
        }

        @Override
        public String getSql() {
            return this.sql;
        }
    }

    // TREATY
    private class ELTMapper implements RowMapper<EventLoss> {
        private double minLayerAtt;

        public ELTMapper(double threshold) {
            this.minLayerAtt = threshold;
        }

        @Override
        public EventLoss mapRow(ResultSet rs, int rowNum) throws SQLException {
            int eventId = (int) rs.getDouble("event_id");
            double meanLoss = rs.getDouble("loss");
            double stdDevC = rs.getDouble("std_dev_c");
            double stdDevU = rs.getDouble("std_dev_i");
            double exposureValue = rs.getDouble("exposure_value");
            double freq = rs.getDouble("rate");
            return new EventLoss(eventId, meanLoss, stdDevC, stdDevU, exposureValue, freq, minLayerAtt);
        }
    }

    private class RMSEventLossRowMapper implements RowMapper<EventLoss> {

        public RMSEventLossRowMapper() {
        }

        @Override
        public EventLoss mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new EventLoss(
                    rs.getInt(1),
                    rs.getDouble(3),
                    rs.getDouble(5),
                    rs.getDouble(4),
                    rs.getDouble(6),
                    rs.getDouble(2),
                    new Double("0.0")
            );
        }
    }

}
