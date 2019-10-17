package com.scor.rr.importBatch.processing.statistics.rms;

import com.scor.rr.domain.entities.cat.CATObjectGroup;
import com.scor.rr.domain.utils.cat.ModellingResult;
import com.scor.rr.domain.utils.plt.ELT;
import com.scor.rr.domain.utils.plt.ELTSourceStatistics;
import com.scor.rr.domain.utils.plt.SummaryStatistics;
import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
import com.scor.rr.importBatch.processing.domain.ELTData;
import com.scor.rr.importBatch.processing.domain.ELTLoss;
import com.scor.rr.importBatch.processing.statistics.StatisticsExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by U002629 on 07/04/2015.
 */
public class RMSSummaryStatisticsExtractor extends BaseRMSBeanImpl implements StatisticsExtractor {
    private static final Logger log = LoggerFactory.getLogger(RMSSummaryStatisticsExtractor.class);

    //Data
    private ELTData eltData;
    private String summaryStatsQuery;
    private EPSWriter writer2;

    public void setEltData(ELTData eltData) {
        this.eltData = eltData;
    }

    public void setSummaryStatsQuery(String summaryStatsQuery) {
        this.summaryStatsQuery = summaryStatsQuery;
    }

    public void setWriter2(EPSWriter writer2) {
        this.writer2 = writer2;
    }

    @Override
    public Boolean runExtraction() {
        for (String regionPeril : eltData.getRegionPerils()) {
            CATObjectGroup group = getCatObjectGroup();
            Map<String, ModellingResult> modellingResultsByRegionPeril = group.getModellingResultsByRegionPeril();
            ELT elt = modellingResultsByRegionPeril.get(regionPeril).getElt();

            ELTLoss lossData = eltData.getLossDataForRp(regionPeril);
            Integer analysisId = lossData.getAnalysisId();
            ELTSourceStatistics statisticsELT = new ELTSourceStatistics();
            try {
                Map<String, Object> eventsELT = jdbcTemplate.queryForMap(summaryStatsQuery.replaceAll(":rdm:", rdm), analysisId, fpELT);
                statisticsELT.setCovariance((Double) eventsELT.get("COV"));
                statisticsELT.setPurePremium((Double) eventsELT.get("PURE_PREMIUM"));
                statisticsELT.setStandardDeviation((Double) eventsELT.get("STANDARD_DEVIATION"));
                statisticsELT.setStatisticMetricId((Integer) eventsELT.get("EP_TYPE_CODE"));
                statisticsELT.setPdSplit((eventsELT.get("PD_SPLIT_PCT")) == null ? null : Integer.valueOf((int) Math.round((Double) eventsELT.get("PD_SPLIT_PCT"))));
                statisticsELT.setBiSplit((eventsELT.get("BI_SPLIT_PCT")) == null ? null : Integer.valueOf((int) Math.round((Double) eventsELT.get("BI_SPLIT_PCT"))));
                elt.setPdSplit(((Double) eventsELT.get("PD_SPLIT_PCT")).intValue());
                elt.setBiSplit(((Double) eventsELT.get("BI_SPLIT_PCT")).intValue());
            } catch (Exception e) {
                log.error("error with ELT summary statistics", e.getMessage());
            }

            ELTSourceStatistics statisticsStats = null;
            try {
                statisticsStats = new ELTSourceStatistics();
                Map<String, Object> eventsStats = jdbcTemplate.queryForMap(summaryStatsQuery.replaceAll(":rdm:",rdm), analysisId, fpStats);
                statisticsStats.setCovariance((Double) eventsStats.get("COV"));
                statisticsStats.setPurePremium((Double) eventsStats.get("PURE_PREMIUM"));
                statisticsStats.setStandardDeviation((Double) eventsStats.get("STANDARD_DEVIATION"));
                statisticsStats.setStatisticMetricId((Integer) eventsStats.get("EP_TYPE_CODE"));
                statisticsStats.setPdSplit((eventsStats.get("PD_SPLIT_PCT")) == null ? null : Integer.valueOf((int) Math.round((Double) eventsStats.get("PD_SPLIT_PCT"))));
                statisticsStats.setBiSplit((eventsStats.get("BI_SPLIT_PCT")) == null ? null : Integer.valueOf((int) Math.round((Double) eventsStats.get("BI_SPLIT_PCT"))));
            } catch (Exception e) {
                log.error("error with Stats summary statistics", e.getMessage());
            }

            elt.getEltSourceStatistics().add(statisticsELT);
            elt.getEltSourceStatistics().add(statisticsStats);

            SummaryStatistics s1 = new SummaryStatistics(1, statisticsELT.getPurePremium(), statisticsELT.getStandardDeviation(), statisticsELT.getCovariance(), 0.0d, 0.0d,0.0d);
            SummaryStatistics s2 = new SummaryStatistics(1, statisticsStats.getPurePremium(), statisticsStats.getStandardDeviation(), statisticsStats.getCovariance(), 0.0d, 0.0d,0.0d);

            writer2.write(s1, "ELT", regionPeril, fpELT, lossData.getCurrency(), "MODEL");
            writer2.write(s2, "ELT", regionPeril, fpStats, lossData.getCurrency(), "MODEL");
            addMessage("SUM STS", "RMS Summary Statistics extracted OK for " + regionPeril);
        }
        return true;
    }

    @Override
    public Boolean runConformedExtraction() {
        return null;
    }
}
