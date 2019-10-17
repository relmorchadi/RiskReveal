package com.scor.rr.importBatch.processing.statistics.rms;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.scor.rr.domain.entities.ihub.RRFinancialPerspective;
import com.scor.rr.domain.entities.ihub.RRLossTable;
import com.scor.rr.domain.entities.ihub.StatFile;
import com.scor.rr.domain.entities.meta.SourceEpHeader;
import com.scor.rr.domain.entities.references.DefaultReturnPeriod;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.rms.AnalysisFinancialPerspective;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.domain.entities.rms.RmsExchangeRate;
import com.scor.rr.domain.entities.stat.RREPCurve;
import com.scor.rr.domain.entities.stat.RRStatisticHeader;
import com.scor.rr.domain.entities.stat.RRSummaryStatistic;
import com.scor.rr.domain.enums.StatDataType;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
import com.scor.rr.importBatch.processing.statistics.StatisticsExtractor;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.importBatch.processing.ylt.meta.XLTOT;
import com.scor.rr.repository.ihub.RRLossTableRepository;
import com.scor.rr.repository.plt.TTFinancialPerspectiveRepository;
import com.scor.rr.repository.references.DefaultReturnPeriodRepository;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.repository.stat.RRStatisticHeaderRepository;
import com.scor.rr.repository.tracking.ProjectImportLogRepository;
import com.scor.rr.service.RmsDataProviderService;
import com.scor.rr.utils.Step;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by U002629 on 07/04/2015.
 */
public class RMSEPCurveExtractor extends BaseRMSBeanImpl implements StatisticsExtractor {

    private static final Logger log = LoggerFactory.getLogger(RMSEPCurveExtractor.class);

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private RmsDataProviderService rmsDataProvider;

    @Autowired
    private TTFinancialPerspectiveRepository ttFinancialPerspectiveRepository;

//    @Autowired
//    private MongoDBSequence mongoDBSequence;

    @Autowired
    private DefaultReturnPeriodRepository defaultReturnPeriodRepository;

    @Autowired
    private ProjectImportLogRepository projectImportLogRepository;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    @Autowired
    private RRStatisticHeaderRepository rrStatisticHeaderRepository;

    @Autowired
    private RRLossTableRepository rrLossTableRepository;

    private EPCurveWriter epCurveWriter;

    private EPSWriter epsWriter;

    private String fpTYCode = "TY";

    @Override
    public RmsDataProviderService getRmsDataProvider() {
        return rmsDataProvider;
    }

    @Override
    public void setRmsDataProvider(RmsDataProviderService rmsDataProvider) {
        this.rmsDataProvider = rmsDataProvider;
    }


    private List<Double> lerp(List<Double> inputs, List<Double> ascKeys, List<Double> values) {
        List<Double> out = new ArrayList<>();
        for (Double input : inputs) {
            int idx = Collections.binarySearch(ascKeys, input);
            double interped;
            if (idx == -1) { // under the referenced range
                interped = values.get(0);
            } else if (idx == -1 - ascKeys.size()) { // beyond the referenced range
                interped = values.get(ascKeys.size() - 1);
            } else if (idx >= 0) { // it matches one endpoint in RP input list
                interped = values.get(idx);
            } else { // it falls into an interval
                int lowIdx = Math.abs(idx + 2);
                int highIdx = Math.abs(idx + 1);
                Double loKey = ascKeys.get(lowIdx);
                Double hiKey = ascKeys.get(highIdx);
                Double loValue = values.get(lowIdx);
                Double hiValue = values.get(highIdx);

                interped = (input - loKey) * (hiValue - loValue) / (hiKey - loKey) + loValue;
            }
            out.add(interped);
        }
        return out;
    }


    private Map<StatisticMetric, List<RREPCurve>> interpolateEltEPCurve(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve) {
        Map<StatisticMetric, List<RREPCurve>> metricToDrpEPCurve = new HashMap<>();
        List<DefaultReturnPeriod> drpList = defaultReturnPeriodRepository.findAll();
        List<Double> epList = new ArrayList<>(drpList.size());
        for (DefaultReturnPeriod drp : drpList) {
            epList.add(drp.getExcedanceProbability());
        }

        for (Map.Entry<StatisticMetric, List<RREPCurve>> entry : metricToEPCurve.entrySet())
        {
            List<RREPCurve> drpELTEPCurve = new ArrayList<>(epList.size());

            List<Double> ascKeys = new ArrayList<>(entry.getValue().size());
            List<Double> values = new ArrayList<>(entry.getValue().size());

            for (RREPCurve eltepCurve : entry.getValue()) {
                ascKeys.add(eltepCurve.getExceedanceProbability());
                values.add(eltepCurve.getLossAmount());
            }

            List<Double> loss = lerp(epList, ascKeys, values);

            for (int i = 0; i < epList.size(); ++i) {
                drpELTEPCurve.add(new RREPCurve(epList.get(i), loss.get(i)));
            }
            metricToDrpEPCurve.put(entry.getKey(), drpELTEPCurve);
        }

        return metricToDrpEPCurve;
    }

    @Override
    public Boolean runExtraction() {
        log.debug("Starting RMSEPCurveExtractor.runExtraction");
        Date startDate = new Date();
        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 2 EXTRACT_EPCURVE_STATS for this analysis in loop of many analysis :
            // only valid sourceResults after step 1 are converted to bundles
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            //mongoDBSequence.nextSequenceId(projectImportAssetLogA);
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(2);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            RmsAnalysis rmsAnalysis = bundle.getRmsAnalysis();
            List<SourceEpHeader> epHeaders = rmsAnalysis.getSourceEpHeader(); // TODO SourceEpHeader ????

            String anlsId = bundle.getRmsAnalysis().getAnalysisId();
            String rdmName = bundle.getRmsAnalysis().getRdmName();
            Long rdmId = bundle.getRmsAnalysis().getRdmId();

            // TODO replace eltHeader by sourceRRLT ////////////////////
            RRLossTable sourceRRLT = bundle.getSourceRRLT();
//            AnalysisFinancialPerspective selectedFP = eltHeader.getAnalysisFinancialPerspective();
            AnalysisFinancialPerspective selectedFP = bundle.getFinancialPerspective();

            if (selectedFP == null) {
                log.info("No selected financial perspective found for analysis id {} RRLossTable id {} - do nothing", anlsId, sourceRRLT.getRrLossTableId());
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);
                log.info("Finish import progress STEP 2 : EXTRACT_EPCURVE_STATS for analysis: {}", bundle.getSourceResult().getSourceResultId());
                // even if error here, continue to track this analysis, no import decision is made here
                continue;
            }
            log.info("Extracting epCurves and sum stats for RRLossTable {}, anlsId {}, rdm {}, rdmId {}, fp {}", sourceRRLT.getRrLossTableId(), anlsId, rdmName, rdmId, selectedFP.getFullCode());

            // Extract filtered list of fin perspective that has data for EPCurves
            Set<AnalysisFinancialPerspective> sourceFPs = new HashSet<>();
            Collection<AnalysisFinancialPerspective> filteredFPs = new ArrayList<>();
            for (SourceEpHeader epHeader : epHeaders) {
                sourceFPs.add(epHeader.getAnalysisFinancialPerspective());
            }

            // no TY
            filteredFPs = Collections2.filter(sourceFPs, new Predicate<AnalysisFinancialPerspective>() {
                @Override
                public boolean apply(AnalysisFinancialPerspective input) {
                    return !StringUtils.equalsIgnoreCase(input.getCode(), fpTYCode);
                }
            });
            filteredFPs = new ArrayList<>(filteredFPs);
            if (StringUtils.equalsIgnoreCase(selectedFP.getCode(), fpTYCode)) {
                // all non-TY plus the chosen TY
                filteredFPs.add(selectedFP);
            }
            for (AnalysisFinancialPerspective sourceFP : sourceFPs) {
                log.info("Source FP: {}", sourceFP.getFullCode());
            }
            log.info("-- Chosen FP: {}", selectedFP.getFullCode());
            for (AnalysisFinancialPerspective filteredFP : filteredFPs) {
                log.info("Filtered FP: {}", filteredFP.getFullCode());
            }

            // Extraction of EPCurves for all fin perspectives
            Map<AnalysisFinancialPerspective, Map<StatisticMetric, List<RREPCurve>>> fp2ELTEPCurves = new HashMap<>();
            Map<AnalysisFinancialPerspective, RRSummaryStatistic> fp2ELTSumStat = new HashMap<>(); // ELTEPSummaryStatistic

            for (AnalysisFinancialPerspective fp : filteredFPs) {
                Map<StatisticMetric, List<RREPCurve>> metricToEPCurve = new HashMap<>();
                String instanceId;
                if (rmsAnalysis.getRmsModelDatasource() != null && rmsAnalysis.getRmsModelDatasource().getInstanceId() != null) {
                    instanceId = rmsAnalysis.getRmsModelDatasource().getInstanceId();
                } else {
                    log.warn("RmsModelDatasource is null for rmsAnalysis {} - use instanceId from batch", rmsAnalysis.getRmsAnalysisId());
                    instanceId = getInstanceId();
                }
                List<Map<String, Object>> rmsEPCurves = rmsDataProvider.extractAnalysisEpCurves(instanceId, rdmId, rdmName, anlsId, fp.getCode(), fp.getTreatyId());
                for (Map<String, Object> rmsEPCurve : rmsEPCurves) {
                    //Long analysis_id = ((Integer) rmsEPCurve.get("analysis_id")).longValue();
                    //String financialPerspective = (String) rmsEPCurve.get("fin_persp_code");
                    Integer metricCode = (Integer) rmsEPCurve.get("ep_type_code");
                    Double loss = (Double) rmsEPCurve.get("loss");
                    Object epObject = rmsEPCurve.get("exceedance_probability");
                    Double ep = ((BigDecimal) epObject).doubleValue();

                    StatisticMetric metric = StatisticMetric.getFrom(metricCode);
                    if (metricToEPCurve.containsKey(metric)) {
                        metricToEPCurve.get(metric).add(new RREPCurve(ep, loss));
                    } else {
                        List<RREPCurve> list = new ArrayList<>();
                        list.add(new RREPCurve(ep, loss));
                        metricToEPCurve.put(metric, list);
                    }
                }
                Map<StatisticMetric, List<RREPCurve>> metricToDrpEPCurve = interpolateEltEPCurve(metricToEPCurve);
                fp2ELTEPCurves.put(fp, metricToDrpEPCurve);
                log.info("... extracted {} ep curves for fp {}", metricToEPCurve.size(), fp.getCode());

                List<Map<String, Object>> rmsSumStats = rmsDataProvider.extractAnalysisSummaryStats(instanceId, rdmId, rdmName, anlsId, fp.getCode(), fp.getTreatyId());
                Map<String, Object> rmsSumStat = null;
                RRSummaryStatistic sumStat = new RRSummaryStatistic(); // ELTEPSummaryStatistic
                if (rmsSumStats == null || rmsSumStats.isEmpty()) {
                    sumStat.setCov(0d);
                    sumStat.setPurePremium(0d);
                    sumStat.setStandardDeviation(0d);
                } else {
                    rmsSumStat = rmsSumStats.get(0);

                    Long anls_id = ((Integer) rmsSumStat.get("analysis_id")).longValue();
                    // Integer metricCode = (Integer) rmsSumStat.get("ep_type_code"); // useless
                    String fpCode = (String) rmsSumStat.get("fin_persp_code");

                    Double purePremium = (Double) rmsSumStat.get("pure_premium");
                    Double stdDev = (Double) rmsSumStat.get("std_dev");
                    Double cov = (Double) rmsSumStat.get("cov");

                    sumStat.setCov(cov);
                    sumStat.setPurePremium(purePremium);
                    sumStat.setStandardDeviation(stdDev);
                    log.trace("total retrieved stats: {}, rmsSumStat.get(0): fp {}, anlsId {}, fpCode {}, premium {}, sdtDev {}, cov {}", rmsSumStats.size(), fp.getFullCode(), anls_id, fpCode, purePremium, stdDev, cov);
                }
                fp2ELTSumStat.put(fp, sumStat);
            }

            // Creating ELTEPHeader for each financial perspective
//            List<ELTEPHeader> newELTEPHeaders = new ArrayList<>();

            // Creating RRStatis for each financial perspective
            List<RRStatisticHeader> newRrStatisticHeaders = new ArrayList<>();

            for (AnalysisFinancialPerspective fp : filteredFPs) {
                StatFile statFile = new StatFile();

                RRSummaryStatistic sumStats = fp2ELTSumStat.get(fp); // ELTEPSummaryStatistic
                Map<StatisticMetric, List<RREPCurve>> metric2EPCurves = fp2ELTEPCurves.get(fp);

                for (Map.Entry<StatisticMetric, List<RREPCurve>> entry : metric2EPCurves.entrySet()) {
                    RRStatisticHeader rrStatisticHeader = new RRStatisticHeader(entry.getKey(), sumStats, entry.getValue());
                    //mongoDBSequence.nextSequenceId(rrStatisticHeader);
                    rrStatisticHeader.setProjectId(transformationPackage.getProjectId());
                    rrStatisticHeader.setLossDataType(StatDataType.STAT_DATA_TYPE_ELT.toString());
                    rrStatisticHeader.setFinancialPerspective(fp != null ? new RRFinancialPerspective(fp) : null);
                    rrStatisticHeader.setLossTableId(sourceRRLT.getRrLossTableId()); // TODO how to change ?????????????????????????????
                    statFile.getStatisticHeaders().add(rrStatisticHeader.getRrStatisticHeaderId());
                    statFile.setFinancialPerspective(fp != null ? new RRFinancialPerspective(fp) : null);
                    newRrStatisticHeaders.add(rrStatisticHeader);
                }

                log.info("Writing source EP curves and sum stats for fp {}", fp.getFullCode());

                Date staEPC = new Date();
                String epcFname = makeELTEPCurveFilename(bundle.getRrAnalysis().getCreationDate(), bundle.getRrAnalysis().getRegionPeril(), fp.getFullCode(), sourceRRLT.getCurrency(), XLTOT.ORIGINAL, sourceRRLT.getRrLossTableId(), getFileExtension());
                BinFile binFileEPC = epCurveWriter.writeELTEPCurves(metric2EPCurves, epcFname);
                statFile.setEpcFileName(binFileEPC.getFileName());
                statFile.setEpcFilePath(binFileEPC.getPath());
                Date endEPC = new Date();
                float time = (1f * endEPC.getTime() - staEPC.getTime()) / 1000f;
                log.info("Generation time: {} for EPC {}", time, epcFname);

                staEPC = new Date();
                String epsFname = makeELTSummaryStatFilename(bundle.getRrAnalysis().getCreationDate(), bundle.getRrAnalysis().getRegionPeril(), fp.getFullCode(), sourceRRLT.getCurrency(), XLTOT.ORIGINAL, sourceRRLT.getRrLossTableId(), getFileExtension());
                BinFile binFileEPS = epsWriter.writePLTSummaryStatistics(sumStats, epsFname);
                statFile.setEpsFileName(binFileEPS.getFileName());
                statFile.setEpsFileName(binFileEPS.getPath());
                sourceRRLT.getStatFiles().add(statFile);
                endEPC = new Date();
                time = (1f * endEPC.getTime() - staEPC.getTime()) / 1000f;
                log.info("Generation time: {} for EPS {}", time, epsFname);
            }

            rrLossTableRepository.save(sourceRRLT);
            // fill in the up-to-date ELTEPHeaders
//            eltHeader.setELTEPHeaders(newELTEPHeaders);
            rrStatisticHeaderRepository.saveAll(newRrStatisticHeaders);

            // finis step 2 EXTRACT_EPCURVE_STATS for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 2 : EXTRACT_EPCURVE_STATS for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        log.debug("RMSEPCurveExtractor.runExtraction completed");
        return true;
    }

    @Override
    public Boolean runConformedExtraction() {
        log.debug("Starting RMSEPCurveExtractor.runConformedExtraction");

        Date startDate = new Date();
        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 7 EXTRACT_CONFORMED_EPCURVE_STATS for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            //mongoDBSequence.nextSequenceId(projectImportAssetLogA);
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(7);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            // TODO delete them
//            ELTHeader eltHeader = bundle.getSourceELTHeader(); //<--> sourceRRLT
//            ELTHeader confELTHeader = bundle.getConformedELTHeader(); //<--> conformedRRLT

            RRLossTable sourceRRLT = bundle.getSourceRRLT();
            RRLossTable conformedRRLT = bundle.getConformedRRLT();


//            List<ELTEPHeader> confELTEPHeaders = new ArrayList<>(); <--> confRrStatisticHeaders
//            confELTHeader.setELTEPHeaders(confELTEPHeaders); <--> confRrStatisticHeaders

            List<RRStatisticHeader> confRrStatisticHeaders = new ArrayList<>();
            // 1 conformedRRLT --> List<RRStatisticHeader> confRrStatisticHeaders
            // conformedRRLT.setELTEPHeaders(confRrStatisticHeaders); //TODO ?? DBRef to String, 1 way

            Map<AnalysisFinancialPerspective, Map<StatisticMetric, List<RREPCurve>>> fp2Metric2EPCurves = new HashMap<>();
            Map<AnalysisFinancialPerspective, RRSummaryStatistic> fp2SumStat = new HashMap<>(); // ELTEPSummaryStatistic

            double proportion = bundle.getSourceResult().getProportion() == null ? 1: bundle.getSourceResult().getProportion().doubleValue() / 100;
            double multiplier = bundle.getSourceResult().getUnitMultiplier() == null ? 1: bundle.getSourceResult().getUnitMultiplier().doubleValue();
            log.info("Conforming EP curves and sum stats for conformedRRLT {}, proportion {}, multiplier {}", bundle.getConformedRRLT().getRrLossTableId(), proportion, multiplier);

            double exchangeRate = 1.0d;
            if (! conformedRRLT.getCurrency().equals(sourceRRLT.getCurrency())) {
                double sourceExchangeRate = 1.0d;
                double targetExchangeRate = 1.0d;
                for (RmsExchangeRate rmsExchangeRate : bundle.getRmsExchangeRatesOfRRLT()) { // TODO not in this step
                    if (rmsExchangeRate.getCcy().equals(sourceRRLT.getCurrency()))
                        sourceExchangeRate = rmsExchangeRate.getExchangeRate();
                    else if (rmsExchangeRate.getCcy().equals(conformedRRLT.getCurrency()))
                        targetExchangeRate = rmsExchangeRate.getExchangeRate();
                    else
                        log.debug("Something wrong: ccy {} found for source ELT currency {} conformed ELT currency {}", rmsExchangeRate.getCcy(), sourceRRLT.getCurrency(), conformedRRLT.getCurrency());
                }

                exchangeRate = targetExchangeRate / sourceExchangeRate;
            }
            log.debug("source ELT currency {} conformed ELT currency {} exchange rate {}", sourceRRLT.getCurrency(), conformedRRLT.getCurrency(), exchangeRate) ;

//            List<RRStatisticHeader> rrStatisticHeaderList = rrStatisticHeaderRepository.findByLossTableId(bundle.getSourceRRLT().getId());


            for (StatFile statFileSource : bundle.getSourceRRLT().getStatFiles()) {
                StatFile statFileConformed = new StatFile();
                for (String rrStatisticHeaderId : statFileSource.getStatisticHeaders()) {
                    RRStatisticHeader rrStatisticHeader = rrStatisticHeaderRepository.findById(rrStatisticHeaderId).get();
                    //            for (ELTEPHeader eltepHeader : eltHeader.getELTEPHeaders()) {
                    RRFinancialPerspective fp = rrStatisticHeader.getFinancialPerspective();
//                AnalysisFinancialPerspective fp = bundle.getSourceResult().getFinancialPerspective(); // TODO false
                    StatisticMetric metric = rrStatisticHeader.getStatisticData().getStatisticMetric();
                    RRSummaryStatistic confSumStat = conformSummaryStatistic(rrStatisticHeader.getStatisticData().getSummaryStatistic(), proportion, multiplier, exchangeRate); // ELTEPSummaryStatistic
                    List<RREPCurve> confEPCurves = conformELTEPCurves(rrStatisticHeader.getStatisticData().getEpCurves(), proportion, multiplier, exchangeRate);

//                ELTEPHeader confELTEPHeader = new ELTEPHeader();
//                confELTEPHeader.setEltHeader(confELTHeader);
//                confELTEPHeader.setFinancialPerspective(fp);
//                confELTEPHeader.setStatisticMetric(metric);
//                confELTEPHeader.setSummaryStatistic(confSumStat);
//                confELTEPHeader.setEpCurves(confEPCurves);
//
//                mongoDBSequence.nextSequenceId(confELTEPHeader);
//                confELTEPHeaders.add(confELTEPHeader);

                    RRStatisticHeader confRrStatisticHeader = new RRStatisticHeader();
                    //mongoDBSequence.nextSequenceId(confRrStatisticHeader);
                    confRrStatisticHeader.setProjectId(transformationPackage.getProjectId());
                    confRrStatisticHeader.setLossTableId(conformedRRLT.getRrLossTableId());
                    statFileConformed.getStatisticHeaders().add(confRrStatisticHeader.getRrStatisticHeaderId());
                    confRrStatisticHeader.setLossDataType(StatDataType.STAT_DATA_TYPE_ELT.toString());
                    confRrStatisticHeader.setFinancialPerspective(fp);
                    statFileConformed.setFinancialPerspective(fp);
                    confRrStatisticHeader.getStatisticData().setStatisticMetric(metric);
                    confRrStatisticHeader.getStatisticData().setSummaryStatistic(confSumStat);
                    confRrStatisticHeader.getStatisticData().setEpCurves(confEPCurves);

                    confRrStatisticHeaders.add(confRrStatisticHeader);

                    // build map for writing purpose
                    if (fp2Metric2EPCurves.get(fp) == null) {
                        Map<StatisticMetric, List<RREPCurve>> metric2EPCurves = new HashMap<>();
                        metric2EPCurves.put(metric, confEPCurves);
                        fp2Metric2EPCurves.put(new AnalysisFinancialPerspective(fp), metric2EPCurves);
                    } else {
                        fp2Metric2EPCurves.get(fp).put(metric, confEPCurves);
                    }
                    if (fp2SumStat.get(fp) == null) {
                        fp2SumStat.put(new AnalysisFinancialPerspective(fp), confSumStat);
                    }
                }
                conformedRRLT.getStatFiles().add(statFileConformed);
            }


            log.debug("conformed ELTHeader id {} currency {}, ", conformedRRLT.getRrLossTableId(), conformedRRLT.getCurrency());

            for (AnalysisFinancialPerspective fp : fp2SumStat.keySet()) {
                log.info("Writing conformed EP curves and sum stats for fp {}", fp.getFullCode());

                for (StatFile statFileConformed : conformedRRLT.getStatFiles()) {
                    if (statFileConformed.getFinancialPerspective().getCode().equals(fp.getCode())) {
                        // String epcFname = makeELTEPCurveFilename(confELTHeader.getCreatedDate(), eltHeader.getRegionPeril().getRegionPerilCode(), fp.getFullCode(), confELTHeader.getCurrency(), XLTOT.TARGET, confELTHeader.getId(), getFileExtension());
                        String epcFname = makeELTEPCurveFilename(bundle.getRrAnalysis().getCreationDate(), bundle.getRrAnalysis().getRegionPeril(), fp.getFullCode(), conformedRRLT.getCurrency(), XLTOT.TARGET, conformedRRLT.getRrLossTableId(), getFileExtension());
                        BinFile binFileEPC = epCurveWriter.writeELTEPCurves(fp2Metric2EPCurves.get(fp), epcFname);
                        statFileConformed.setEpcFileName(binFileEPC.getFileName());
                        statFileConformed.setEpcFilePath(binFileEPC.getPath());

                        // String epsFname = makeELTSummaryStatFilename(confELTHeader.getCreatedDate(), eltHeader.getRegionPeril().getRegionPerilCode(), fp.getFullCode(), confELTHeader.getCurrency(), XLTOT.TARGET, confELTHeader.getId(), getFileExtension());
                        String epsFname = makeELTSummaryStatFilename(bundle.getRrAnalysis().getCreationDate(), bundle.getRrAnalysis().getRegionPeril(), fp.getFullCode(), conformedRRLT.getCurrency(), XLTOT.TARGET, conformedRRLT.getRrLossTableId(), getFileExtension());
                        BinFile binFileEPS = epsWriter.writePLTSummaryStatistics(fp2SumStat.get(fp), epsFname);
                        statFileConformed.setEpsFileName(binFileEPS.getFileName());
                        statFileConformed.setEpsFilePath(binFileEPS.getPath());
                        break;
                    }
                }
            }

            rrLossTableRepository.save(conformedRRLT);
//            eltepHeaderRepository.save(confELTEPHeaders);
            rrStatisticHeaderRepository.saveAll(confRrStatisticHeaders);

            // finish step 7 EXTRACT_CONFORMED_EPCURVE_STATS for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 7 : EXTRACT_CONFORMED_EPCURVE_STATS for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        log.debug("RMSEPCurveExtractor.runConformedExtraction completed");
        return true;
    }

    // all is ELTEPSummaryStatistic
    private RRSummaryStatistic conformSummaryStatistic(RRSummaryStatistic input, double proportion, double multiplier, double exchangeRate) {
        RRSummaryStatistic output = new RRSummaryStatistic();
        output.setCov(input.getCov());
        output.setPurePremium(input.getPurePremium() * proportion * multiplier * exchangeRate);
        output.setStandardDeviation(input.getStandardDeviation() * proportion * multiplier * exchangeRate);
        return output;
    }

    private List<RREPCurve> conformELTEPCurves(List<RREPCurve> inputs, double proportion, double multiplier, double exchangeRate) {
        List<RREPCurve> outputs = new ArrayList<>();
        for (RREPCurve input : inputs) {
            RREPCurve output = new RREPCurve();
            outputs.add(output);

            output.setExceedanceProbability(input.getExceedanceProbability());
            output.setReturnPeriod(input.getReturnPeriod());
            output.setLossAmount(input.getLossAmount() * proportion * multiplier * exchangeRate);
        }
        return outputs;
    }

    public EPCurveWriter getEpCurveWriter() {
        return epCurveWriter;
    }

    public void setEpCurveWriter(EPCurveWriter epCurveWriter) {
        this.epCurveWriter = epCurveWriter;
    }

    public EPSWriter getEpsWriter() {
        return epsWriter;
    }

    public void setEpsWriter(EPSWriter epsWriter) {
        this.epsWriter = epsWriter;
    }
}
