package com.scor.rr.service.adjustement.pltAdjustment;

import com.google.common.collect.Lists;
import com.scor.rr.configuration.UtilsMethod;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.EPMetric;
import com.scor.rr.domain.dto.EPMetricPoint;
import com.scor.rr.domain.dto.SummaryStatisticType;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalculateAdjustmentService {

    private static final Logger log = LoggerFactory.getLogger(CalculateAdjustmentService.class);
    private static final double CONSTANT = 100000;

    @Autowired
    PltHeaderRepository pltHeaderRepository;

    @Autowired
    ModelAnalysisEntityRepository modelAnalysisEntityRepository;

    @Autowired
    SummaryStatisticHeaderRepository summaryStatisticHeaderRepository;

    @Autowired
    LossDataHeaderEntityRepository lossDataHeaderEntityRepository;

    @Autowired
    SummaryStatisticsDetailRepository summaryStatisticsDetailRepository;

    @Autowired
    DefaultReturnPeriodRepository defaultReturnPeriodRepository;

    @Autowired
    RegionPerilRepository regionPerilRepository;

    public SummaryStatisticHeaderEntity calculateSummaryStatistic(Long pltId) throws RRException {
        PltHeaderEntity plt = pltHeaderRepository.findByPltHeaderId(pltId);

        String fullFilePath = plt.getLossDataFilePath() + "/" + plt.getLossDataFileName(); // "/" linux

        Optional<ModelAnalysisEntity> modelAnalysisOptional = modelAnalysisEntityRepository.findById(plt.getModelAnalysisId());
        Optional<RegionPerilEntity> regionPerilEntityOptional = regionPerilRepository.findById(plt.getRegionPerilId());

        if (modelAnalysisOptional.isPresent() && regionPerilEntityOptional.isPresent()) {
            if (plt.getModelAnalysisId() == null) {
                throw new IllegalStateException("calculateSummaryStatisticHeaderDetail, plt.getModelAnalysisId() null, wrong");
            }
            if (plt.getModelAnalysisId() == null) {
                throw new IllegalStateException("calculateSummaryStatisticHeaderDetail, lossDataHeader null, wrong");
            }
            LossDataHeaderEntity lossDataHeader = lossDataHeaderEntityRepository.findByModelAnalysisId(plt.getModelAnalysisId());

            List<SummaryStatisticHeaderEntity> summaryStatisticHeaders = summaryStatisticHeaderRepository.findByLossDataIdAndLossDataType(lossDataHeader.getLossDataHeaderId(), "PLT");
            if (summaryStatisticHeaders != null && !summaryStatisticHeaders.isEmpty()) {
                return summaryStatisticHeaders.get(0);
            }

            SummaryStatisticHeaderEntity summaryStatisticHeaderEntity = new SummaryStatisticHeaderEntity();

            MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
            List<PLTLossData> pltLossData = readPltFile.read(new File(fullFilePath));

            // todo writeSummaryStat
            summaryStatisticHeaderEntity.setPurePremium(StatisticAdjustment.averageAnnualLoss(pltLossData));
            summaryStatisticHeaderEntity.setCov(StatisticAdjustment.coefOfVariance(pltLossData));
            summaryStatisticHeaderEntity.setStandardDeviation(StatisticAdjustment.stdDev(pltLossData));
            summaryStatisticHeaderEntity.setLossDataType("PLT");
            summaryStatisticHeaderEntity.setLossDataId(lossDataHeader.getLossDataHeaderId()); // todo right ?
            summaryStatisticHeaderEntity.setFinancialPerspective("FP"); // todo right ?
            summaryStatisticHeaderEntity.setEntity(1L);
            summaryStatisticHeaderEntity.setCurrency(plt.getCurrencyCode());
            summaryStatisticHeaderRepository.save(summaryStatisticHeaderEntity);

            // summaryStatisticsDetail
            EPMetric aepMetric = getAEPMetric(pltLossData);
            EPMetric oepMetric = getOEPMetric(pltLossData);
            EPMetric aepTvarMetric = StatisticAdjustment.AEPTVaRMetrics(getAEPMetric(pltLossData).getEpMetricPoints());
            EPMetric oepTvarMetric = StatisticAdjustment.OEPTVaRMetrics(getAEPMetric(pltLossData).getEpMetricPoints());

            SummaryStatisticsDetail aepSummaryStatisticsDetail = getSummaryStatisticsDetail(pltId, aepMetric, "AEP", summaryStatisticHeaderEntity);
            SummaryStatisticsDetail oepSummaryStatisticsDetail = getSummaryStatisticsDetail(pltId, oepMetric, "OEP", summaryStatisticHeaderEntity);
            SummaryStatisticsDetail aepTvarSummaryStatisticsDetail = getSummaryStatisticsDetail(pltId, aepTvarMetric, "AEPTVAR", summaryStatisticHeaderEntity);
            SummaryStatisticsDetail oepTvarSummaryStatisticsDetail = getSummaryStatisticsDetail(pltId, oepTvarMetric, "OEPTVAR", summaryStatisticHeaderEntity);

            return summaryStatisticHeaderEntity;

        } else {
            log.error("no model analysis found for plt with id {}", plt.getPltHeaderId());
            return null;
        }
    }

    public SummaryStatisticsDetail getSummaryStatisticsDetail(Long pltId, EPMetric epMetric, String curveType, SummaryStatisticHeaderEntity summaryStatisticHeaderEntity) {
        SummaryStatisticsDetail summaryStatisticsDetail = summaryStatisticsDetailRepository.findByPltHeaderIdAndCurveTypeAndLossType(pltId, curveType, "PLT");
        if (summaryStatisticsDetail != null) {
            return summaryStatisticsDetail;
        }
        summaryStatisticsDetail = new SummaryStatisticsDetail();
        summaryStatisticsDetail.setPltHeaderId(pltId);
        summaryStatisticsDetail.setCurveType(curveType);
        summaryStatisticsDetail.setLossType("PLT");
        summaryStatisticsDetail.setSummaryStatisticHeaderId(summaryStatisticHeaderEntity.getSummaryStatisticHeaderId());

        Map<Integer, Integer> mapRef = new HashMap<>();
        List<DefaultReturnPeriodEntity> defaultReturnPeriodEntities = defaultReturnPeriodRepository.findAll();
        if (defaultReturnPeriodEntities == null || defaultReturnPeriodEntities.isEmpty()) {
            throw new IllegalStateException("calculateSummaryStatistic, no defaultReturnPeriodEntities, wrong");
        }
        for (DefaultReturnPeriodEntity defaultReturnPeriodEntity : defaultReturnPeriodEntities) {
            mapRef.put(defaultReturnPeriodEntity.getRank(), defaultReturnPeriodEntity.getReturnPeriod());
        }

        Map<Integer, Double> map = new HashMap<>();
        for (EPMetricPoint epMetricPoint : epMetric.getEpMetricPoints()) {
            if (mapRef.get(epMetricPoint.getRank()) != null) {
                map.put(mapRef.get(epMetricPoint.getRank()), epMetricPoint.getLoss());
            }
        }

        // for summaryStatisticHeader 8 points
        if ("AEP".equals(curveType)) {
            summaryStatisticHeaderEntity.setAep2(map.get(2));
            summaryStatisticHeaderEntity.setAep5(map.get(5));
            summaryStatisticHeaderEntity.setAep10(map.get(10));
            summaryStatisticHeaderEntity.setAep50(map.get(50));
            summaryStatisticHeaderEntity.setAep100(map.get(100));
            summaryStatisticHeaderEntity.setAep250(map.get(250));
            summaryStatisticHeaderEntity.setAep500(map.get(500));
            summaryStatisticHeaderEntity.setAep1000(map.get(1000));
        } else if ("OEP".equals(curveType)) {
            summaryStatisticHeaderEntity.setOep2(map.get(2));
            summaryStatisticHeaderEntity.setOep5(map.get(5));
            summaryStatisticHeaderEntity.setOep10(map.get(10));
            summaryStatisticHeaderEntity.setOep50(map.get(50));
            summaryStatisticHeaderEntity.setOep100(map.get(100));
            summaryStatisticHeaderEntity.setOep250(map.get(250));
            summaryStatisticHeaderEntity.setOep500(map.get(500));
            summaryStatisticHeaderEntity.setOep1000(map.get(1000));
        }
        summaryStatisticHeaderRepository.save(summaryStatisticHeaderEntity);

        // todo : for summaryStatisticsDetail 630 points
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            columns.add("RP" + entry.getKey());
            values.add(entry.getValue().toString());
        }

        String query = "insert SummaryStatisticHeaderDetail  (" + StringUtils.join(columns, ",") + ") values (" + StringUtils.join(values, ",") + ");";
        summaryStatisticsDetailRepository.save(summaryStatisticsDetail);
        return summaryStatisticsDetail;
    }

    public static EPMetric getOEPMetric(List<PLTLossData> pltLossDatas){
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            int[] finalI = new int[]{0};
            List<PLTLossData> finalPltLossDatas1 = pltLossDatas;
            pltLossDatas = pltLossDatas.stream().map(pltLossData -> new PLTLossData(pltLossData.getSimPeriod(),pltLossData.getEventId(),pltLossData.getEventDate(),pltLossData.getSeq(),pltLossData.getMaxExposure(), finalPltLossDatas1.stream().filter(pltLossData1 -> pltLossData1.getSimPeriod()==pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).get().getLoss())).filter(UtilsMethod.distinctByKey(PLTLossData::getSimPeriod)).collect(Collectors.toList());
            return new EPMetric(StatisticMetric.OEP,
                    pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossDataVar -> {
                finalI[0]++;
                return new EPMetricPoint(finalI[0] / CONSTANT, CONSTANT / finalI[0], pltLossDataVar.getLoss(), finalI[0]);
            }).collect(Collectors.toList()));
        } else {
            log.info("plt empty");
            return null;
        }
    }

    public static EPMetric getAEPMetric(List<PLTLossData> pltLossDatas){
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            int[] finalI = new int[]{0};
            List<PLTLossData> finalPltLossDatas1 = pltLossDatas;
            pltLossDatas = pltLossDatas.stream().map(pltLossData -> new PLTLossData(pltLossData.getSimPeriod(),pltLossData.getEventId(),pltLossData.getEventDate(),pltLossData.getSeq(),pltLossData.getMaxExposure(), finalPltLossDatas1.stream().filter(pltLossData1 -> pltLossData1.getSimPeriod()==pltLossData.getSimPeriod()).mapToDouble(PLTLossData::getLoss).sum())).filter(UtilsMethod.distinctByKey(PLTLossData::getSimPeriod)).collect(Collectors.toList());
            return new EPMetric(StatisticMetric.AEP,
                    pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(
                    pltLossData -> {
                        finalI[0]++;
                            return new EPMetricPoint(finalI[0] / CONSTANT,
                                    CONSTANT / finalI[0] ,
                                    pltLossData.getLoss(),
                                    finalI[0]);
                    }).distinct().collect(Collectors.toList()));
        } else {
            log.info("plt empty");
            return null;
        }
    }

    private Double interpolation(double x, double yMax, double yMin, double xMin, double xMax){
        return yMin+((yMax-yMin)*((x-xMin)/(xMax-xMin)));
    }

    public List<PLTLossData> oepReturnBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBendings) {
        if (pltLossDatas != null && !pltLossDatas.isEmpty()) {
            if (adjustmentReturnPeriodBendings != null && !adjustmentReturnPeriodBendings.isEmpty()) {
                List<EPMetricPoint> oepMetrics = CalculateAdjustmentService.getOEPMetric(pltLossDatas).getEpMetricPoints();
                if(oepMetrics != null && !oepMetrics.isEmpty()) {
                    return pltLossDatas
                            .stream()
                            .sorted(Comparator.comparing(PLTLossData::getLoss).reversed())
                            .map(pltLossData -> {
                                EPMetricPoint maxRpOep = oepMetrics.stream().filter(oepMetric -> oepMetric.getLoss() >= pltLossData.getLoss()).min(Comparator.comparingDouble(EPMetricPoint::getLoss)).orElse(null);
                                EPMetricPoint minRpOep = oepMetrics.stream().filter(oepMetric -> oepMetric.getLoss() <= pltLossData.getLoss()).max(Comparator.comparingDouble(EPMetricPoint::getLoss)).orElse(null);
                                if(maxRpOep == null && minRpOep != null) {
                                    double rpSearch = minRpOep.getReturnPeriod();
                                    ReturnPeriodBandingAdjustmentParameter maxRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() >= rpSearch).min(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                                    ReturnPeriodBandingAdjustmentParameter minRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() <= rpSearch).max(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                                    if(maxRp == null&& minRp != null) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * minRp.getAdjustmentFactor() ,
                                                cap ? Double.min(minRp.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : minRp.getAdjustmentFactor()   * pltLossData.getLoss()
                                        );
                                    }
                                    if(minRp==null && maxRp!=null) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * ((((maxRp.getAdjustmentFactor()) * ((rpSearch) / (maxRp.getReturnPeriod()))))),
                                                cap ? Double.min((( ((maxRp.getAdjustmentFactor()) * ((rpSearch) / (maxRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : (( ((maxRp.getAdjustmentFactor()) * ((rpSearch) / (maxRp.getReturnPeriod()))))) * pltLossData.getLoss()
                                        );
                                    }
                                    if(maxRp.equals(minRp)) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                                cap ? Double.min(maxRp.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : maxRp.getAdjustmentFactor() * pltLossData.getLoss()
                                        );
                                    } else {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                                cap ? Double.min(((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((rpSearch - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : ((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((rpSearch - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod())))))  * pltLossData.getLoss()
                                        );
                                    }
                                }
                                if(minRpOep == null && maxRpOep!=null) {
                                    double rpSearch = (( ((maxRpOep.getReturnPeriod()) * ((pltLossData.getLoss()) / (maxRpOep.getLoss())))));
                                    ReturnPeriodBandingAdjustmentParameter maxRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() >= rpSearch).min(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                                    ReturnPeriodBandingAdjustmentParameter minRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() <= rpSearch).max(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                                    if(maxRp == null&& minRp != null) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * minRp.getAdjustmentFactor() ,
                                                cap ? Double.min(minRp.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : minRp.getAdjustmentFactor()  * pltLossData.getLoss()
                                        );
                                    }
                                    if(minRp==null && maxRp!=null) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * ((((maxRp.getAdjustmentFactor()) * ((rpSearch) / (maxRp.getReturnPeriod()))))),
                                                cap ? Double.min((( ((maxRp.getAdjustmentFactor()) * ((rpSearch) / (maxRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : ((((maxRp.getAdjustmentFactor()) * ((rpSearch) / (maxRp.getReturnPeriod()))))) * pltLossData.getLoss()
                                        );
                                    }
                                    if(maxRp.equals(minRp)) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                                cap ? Double.min(maxRp.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : maxRp.getAdjustmentFactor() * pltLossData.getLoss()
                                        );
                                    } else {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                                cap ? Double.min(((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((rpSearch - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : ((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((rpSearch - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss()
                                        );
                                    }
                                }
                                if (minRpOep.equals(maxRpOep)) {
                                    ReturnPeriodBandingAdjustmentParameter maxRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() >= maxRpOep.getReturnPeriod()).min(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                                    ReturnPeriodBandingAdjustmentParameter minRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() <= maxRpOep.getReturnPeriod()).max(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                                    if(maxRp == null && minRp != null) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * minRp.getAdjustmentFactor(),
                                                cap ? Double.min(minRp.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : minRp.getAdjustmentFactor() * pltLossData.getLoss()
                                        );
                                    }
                                    if(minRp==null && maxRp != null) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                                cap ? Double.min(((((maxRp.getAdjustmentFactor()) * ((maxRpOep.getReturnPeriod()) / (maxRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : ((((maxRp.getAdjustmentFactor()) * ((maxRpOep.getReturnPeriod()) / (maxRp.getReturnPeriod()))))) * pltLossData.getLoss()
                                        );
                                    }
                                    if(maxRp.equals(minRp)) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                                cap ? Double.min(maxRp.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : maxRp.getAdjustmentFactor() * pltLossData.getLoss()
                                        );
                                    } else {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                                cap ? Double.min(((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((maxRpOep.getReturnPeriod() - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : ((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((maxRpOep.getReturnPeriod() - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss()
                                        );
                                    }
                                } else {
                                    double rpSearch = ((minRpOep.getReturnPeriod() + ((maxRpOep.getReturnPeriod() - minRpOep.getReturnPeriod()) * ((pltLossData.getLoss() - minRpOep.getLoss()) / (maxRpOep.getLoss() - minRpOep.getLoss())))));
                                    ReturnPeriodBandingAdjustmentParameter maxRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() >= rpSearch).min(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                                    ReturnPeriodBandingAdjustmentParameter minRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() <= rpSearch).max(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                                    if(maxRp == null&& minRp != null) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * minRp.getAdjustmentFactor(),
                                                cap ? Double.min(minRp.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : minRp.getAdjustmentFactor()  * pltLossData.getLoss()
                                        );
                                    }
                                    if(minRp==null && maxRp!=null) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * ((((maxRp.getAdjustmentFactor()) * ((rpSearch) / (maxRp.getReturnPeriod()))))),
                                                cap ? Double.min((( ((maxRp.getAdjustmentFactor()) * ((rpSearch) / (maxRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : (( ((maxRp.getAdjustmentFactor()) * ((rpSearch) / (maxRp.getReturnPeriod()))))) * pltLossData.getLoss()
                                        );
                                    }
                                    if(maxRp.equals(minRp)) {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                                cap ? Double.min(maxRp.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : maxRp.getAdjustmentFactor() * pltLossData.getLoss()
                                        );
                                    } else {
                                        return new PLTLossData(pltLossData.getSimPeriod(),
                                                pltLossData.getEventId(),
                                                pltLossData.getEventDate(),
                                                pltLossData.getSeq(),
                                                cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                                cap ? Double.min(((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((rpSearch - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : ((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((rpSearch - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss()
                                        );
                                    }
                                }
                            }).collect(Collectors.toList());
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private List<PLTLossData> oepAndEEFReturnBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings, String oepEEF) {
        if (pltLossDatas != null && !pltLossDatas.isEmpty()) {
            int[] i = {0};
            int[] finalI = i;
            List<List<Double>> returnPeriods = pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                finalI[0]++;
                return new ArrayList<Double>() {{
                    add((CalculateAdjustmentService.CONSTANT / finalI[0])); //return period
                    add(pltLossData.getLoss()); //loss
                }};
            }).collect(Collectors.toList());
            if (adjustmentReturnPeriodBandings != null && !adjustmentReturnPeriodBandings.isEmpty()) {
                ReturnPeriodBandingAdjustmentParameter minRpAll = adjustmentReturnPeriodBandings.stream().min(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                i = new int[]{-1};
                int[] finalI1 = i;
                ReturnPeriodBandingAdjustmentParameter maxReturnPeriodBanding = adjustmentReturnPeriodBandings.stream().max(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                return pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                            finalI1[0]++;
                    ReturnPeriodBandingAdjustmentParameter maxRp = adjustmentReturnPeriodBandings.stream().filter(adjustmentReturnPeriodBanding -> adjustmentReturnPeriodBanding.getReturnPeriod() >= returnPeriods.get(finalI1[0]).get(0)).min(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                    ReturnPeriodBandingAdjustmentParameter minRp = adjustmentReturnPeriodBandings.stream().filter(adjustmentReturnPeriodBanding -> adjustmentReturnPeriodBanding.getReturnPeriod() <= returnPeriods.get(finalI1[0]).get(0)).max(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                            if (minRp == null && maxRp != null) {
                                if (oepEEF.equals("OEP")) {
                                    double loss = pltLossDatas.stream().filter(pltLossDatafilter -> pltLossDatafilter.getSimPeriod() == pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).orElse(new PLTLossData()).getLoss();
                                    return new PLTLossData(pltLossData.getSimPeriod(),
                                            pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * ((minRpAll.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0)) / (minRpAll.getReturnPeriod()))),
                                            cap ? Double.min(((minRpAll.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0)) / (minRpAll.getReturnPeriod()))) * loss, pltLossData.getMaxExposure() ) : (((maxRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0)) / (minRpAll.getReturnPeriod()))) * loss));
                                } else {
                                    return new PLTLossData(pltLossData.getSimPeriod(),
                                            pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * ((maxRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0)) / (maxRp.getReturnPeriod()))),
                                            cap ? Double.min(((maxRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0)) / (maxRp.getReturnPeriod()))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : (((maxRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0)) / (maxRp.getReturnPeriod()))) * pltLossData.getLoss()));
                                }
                            } else if (maxRp == null) {
                                return getPltLossData(cap, oepEEF, maxReturnPeriodBanding, pltLossDatas, pltLossData);
                            } else if (maxRp.equals(minRp)) {
                                return getPltLossData(cap, oepEEF, maxRp, pltLossDatas, pltLossData);
                            } else if (returnPeriods.get(finalI1[0]).get(0).equals(maxRp.getReturnPeriod())) {
                                if (oepEEF.equals("OEP")) {
                                    double loss = pltLossDatas.stream().filter(pltLossDatafilter -> pltLossDatafilter.getSimPeriod() == pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).orElse(new PLTLossData()).getLoss();
                                    return new PLTLossData(pltLossData.getSimPeriod(),
                                            pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getAdjustmentFactor(),
                                            cap ? Double.min(maxRp.getAdjustmentFactor() * loss, pltLossData.getMaxExposure()) : maxRp.getAdjustmentFactor() * loss);
                                } else {
                                    return new PLTLossData(pltLossData.getSimPeriod(),
                                            pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure()*maxRp.getAdjustmentFactor(),
                                            cap ? Double.min(maxRp.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : maxRp.getAdjustmentFactor() * pltLossData.getLoss());
                                }
                            } else {
                                if (oepEEF.equals("OEP")) {
                                    double loss = pltLossDatas.stream().filter(pltLossDatafilter -> pltLossDatafilter.getSimPeriod() == pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).orElse(new PLTLossData()).getLoss();
                                    return new PLTLossData(pltLossData.getSimPeriod(),
                                            pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))) * pltLossData.getMaxExposure(),
                                            cap ? Double.min(((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod())))))  * loss, pltLossData.getMaxExposure() ) : ((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * loss);
                                } else {
                                    return new PLTLossData(pltLossData.getSimPeriod(),
                                            pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * (minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod())))),
                                            cap ? Double.min(((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure() ) :((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriods.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss());
                                }
                            }
                        }
                ).collect(Collectors.toList());
            } else {
                log.info("Adjustment empty");
                return null;
            }
        } else {
            log.info("plt empty");
            return null;
        }
    }

    private static final boolean DBG = false;
    private static final int nYears = 100000;
    // ensure the list to be sorted in increasing order
    private static List<Double> inReturnPeriods;
    // factors can be non-increasing but the adjusted PLT must be increasing
    private static List<Double> inFactors;

    // sortedList : input PLT
    public static List<PLTLossData> OEPReturnBanding(List<PLTLossData> sortedList, boolean isCapped, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        SortedMap<Double, Double> inRPToLMF = new TreeMap<>();
        for (ReturnPeriodBandingAdjustmentParameter param : adjustmentReturnPeriodBandings) {
            inRPToLMF.put(param.getReturnPeriod(), param.getAdjustmentFactor());
        }

        inReturnPeriods = new ArrayList<>(inRPToLMF.keySet());
        inFactors = new ArrayList<>(inRPToLMF.values());

        if (!TransformationUtils.isSortedReversely(sortedList)) {
            log.info("PLTLossData is being resorted for adjustment");
            TransformationUtils.sortReverse(sortedList);
        }

        List<PLTLossData> resultList = new ArrayList<>();
        for (PLTLossData lossData : sortedList) {
            resultList.add(new PLTLossData(lossData));
        }

        if (DBG) log.info("Building OEP tables");
        Map<Integer, PLTLossData> periodToLossData = new LinkedHashMap<>(); // already sorted by loss thanks to sortedList
        for (PLTLossData lossData : resultList) {
            Integer period = Integer.valueOf(lossData.getSimPeriod());

            boolean is30589 = period == 30589;
            if (periodToLossData.containsKey(period)) {
                if (periodToLossData.get(period).getLoss() >= lossData.getLoss()) {
                    if (DBG && is30589) if (DBG) log.info("skip {} - {} into oep table", period, lossData.getLoss());
                    continue;
                }
                if (DBG && is30589) if (DBG) log.info("override to put {} - {} into oep table", period, lossData.getLoss());
            }
            periodToLossData.put(period, lossData);
            if (DBG && is30589) if (DBG) log.info("put {} - {} into oep table", period, lossData.getLoss());
        }

        int rank = 0;
        List<Double> descOEPRP = new ArrayList<>();
        List<Double> descOEPLoss = new ArrayList<>();
        for (Map.Entry<Integer, PLTLossData> entry : periodToLossData.entrySet()) {
            rank++;
            descOEPRP.add(Double.valueOf(1d * nYears / rank));
            descOEPLoss.add(entry.getValue().getLoss());
        }

        List<Double> ascOEPRP = Lists.reverse(descOEPRP);
        List<Double> ascOEPLoss = Lists.reverse(descOEPLoss);

        // TODO - removable
        if (TransformationUtils.isSortedReversely(ascOEPRP)) {
            throw new IllegalStateException("isSortedReversely oep ascOEPRP");
        }
        if (TransformationUtils.isSortedReversely(ascOEPLoss)) {
            throw new IllegalStateException("isSortedReversely oep ascOEPLoss");
        }

        if (DBG) log.info("Extracting/interpolating OEP loss profile");
        List<Double> inLossProfile = TransformationUtils.lerp(inReturnPeriods, ascOEPRP, ascOEPLoss);

        if (DBG) log.info("Interpolating lmf profile");
        List<Double> outFactors = lerp(resultList, inLossProfile, inFactors);

        if (DBG) log.info("Adjusting loss");
        if (isCapped) {
            for (int i = 0; i < resultList.size(); i++) {
                PLTLossData lossData = resultList.get(i);
                lossData.setLoss(Math.min(lossData.getLoss() * outFactors.get(i), lossData.getMaxExposure()));
            }
        } else {
            for (int i = 0; i < resultList.size(); i++) {
                PLTLossData lossData = resultList.get(i);
                lossData.setLoss(lossData.getLoss() * outFactors.get(i));
                lossData.setMaxExposure(lossData.getMaxExposure() * outFactors.get(i));
            }
        }

        return resultList;
    }

    public static List<Double> lerp(List<PLTLossData> inputs, List<Double> ascLossOEP, List<Double> factors) {
        if (!TransformationUtils.isSorted(ascLossOEP)) {
            throw new IllegalStateException("Input keys not sorted ascendingly");
        }
        if (ascLossOEP.size() != factors.size()) {
            throw new IllegalStateException("Input keys and values not having same size");
        }
        List<Double> outFactors = new ArrayList<>();

        for (PLTLossData input : inputs) {
            double loss = input.getLoss();
            int idx = Collections.binarySearch(ascLossOEP, loss);
            double interped;
            if (idx == -1) { // under the referenced range
                interped = factors.get(0);
            } else if (idx == -1 - ascLossOEP.size()) { // beyond the referenced range
                interped = factors.get(ascLossOEP.size() - 1);
            } else if (idx >= 0) { // it matches one endpoint in RP input list
                interped = factors.get(idx);
            } else { // it falls into an interval
                int loIdx = Math.abs(idx + 2);
                int hiIdx = Math.abs(idx + 1);
                Double loLoss = ascLossOEP.get(loIdx);
                Double hiLoss = ascLossOEP.get(hiIdx);
                Double loFactor = factors.get(loIdx);
                Double hiFactor = factors.get(hiIdx);

                interped = (loss - loLoss) * (hiFactor - loFactor) / (hiLoss - loLoss) + loFactor;
            }
            outFactors.add(interped);
        }
        return outFactors;
    }

    private static PLTLossData getPltLossData(boolean cap, String oepEEF, ReturnPeriodBandingAdjustmentParameter maxReturnPeriodBanding, List<PLTLossData> finalPltLossDatas, PLTLossData pltLossData) {
        if (oepEEF.equals("OEP")) {
            double loss = finalPltLossDatas.stream().filter(pltLossDatafilter -> pltLossDatafilter.getSimPeriod() == pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).get().getLoss();
            return new PLTLossData(pltLossData.getSimPeriod(),
                    pltLossData.getEventId(),
                    pltLossData.getEventDate(),
                    pltLossData.getSeq(),
                    cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxReturnPeriodBanding.getAdjustmentFactor(),
                    cap ? Double.min(maxReturnPeriodBanding.getAdjustmentFactor() * loss, pltLossData.getMaxExposure()) : maxReturnPeriodBanding.getAdjustmentFactor() * loss);
        } else {
            return new PLTLossData(pltLossData.getSimPeriod(),
                    pltLossData.getEventId(),
                    pltLossData.getEventDate(),
                    pltLossData.getSeq(),
                    cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxReturnPeriodBanding.getAdjustmentFactor(),
                    cap ? Double.min(maxReturnPeriodBanding.getAdjustmentFactor() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : maxReturnPeriodBanding.getAdjustmentFactor() * pltLossData.getLoss());
        }
    }

    public List<PLTLossData> oepReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        return oepAndEEFReturnBanding(pltLossDatas, cap, adjustmentReturnPeriodBandings, "OEP");
    }

    public List<PLTLossData> eefReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        return oepAndEEFReturnBanding(pltLossDatas, cap, adjustmentReturnPeriodBandings, "EEF");
    }

    public List<PLTLossData> eefFrequency(List<PLTLossData> pltLossDatas, boolean cap, double rpmf) {
        if (pltLossDatas != null && !pltLossDatas.isEmpty()) {
            if (rpmf > 0) {
                int[] i = {0};
                int[] finalI = i;
                List<List<Double>> adjustedReturnPeriosd = pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                    finalI[0]++;

                    return new ArrayList<Double>() {{
                        add(((CONSTANT / finalI[0]) * rpmf));//adjusted return period
                        add((CONSTANT / finalI[0]));//return period
                        add(pltLossData.getLoss());//loss
                    }};
                }).collect(Collectors.toList());

                double maxLoss = pltLossDatas.stream().max(Comparator.comparing(PLTLossData::getLoss)).orElse(new PLTLossData(0,
                        0,
                         0,
                        0,
                        (double) 0, (double) 0)).getLoss();
                i = new int[]{-1};
                int[] finalI1 = i;
                pltLossDatas = pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                    finalI1[0]++;
                    List<Double> maxRp = adjustedReturnPeriosd.stream().filter(doubles -> doubles.get(1) >= adjustedReturnPeriosd.get(finalI1[0]).get(0)).min(Comparator.comparingDouble(o -> o.get(1))).orElse(null);
                    List<Double> minRp = adjustedReturnPeriosd.stream().filter(doubles -> doubles.get(1) <= adjustedReturnPeriosd.get(finalI1[0]).get(0)).max(Comparator.comparingDouble(o -> o.get(1))).orElse(null);

                    if (minRp == null) {
                        return new PLTLossData(pltLossData.getSimPeriod(),
                                pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                0);
                    }

                    if (maxRp == null) {
                        return new PLTLossData(pltLossData.getSimPeriod(),
                                pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                maxLoss);
                    } else if (maxRp.equals(minRp)) {
                        return new PLTLossData(pltLossData.getSimPeriod(),
                                pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                maxRp.get(2));
                    } else if (adjustedReturnPeriosd.get(finalI1[0]).get(0).equals(maxRp.get(1))) {
                        return new PLTLossData(pltLossData.getSimPeriod(),
                                pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                maxRp.get(2));
                    } else {
                        return new PLTLossData(pltLossData.getSimPeriod(),
                                pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                cap ? Double.min(minRp.get(2) + ((maxRp.get(2) - minRp.get(2)) * ((adjustedReturnPeriosd.get(finalI1[0]).get(0) - minRp.get(1)) / (maxRp.get(1) - minRp.get(1)))), pltLossData.getMaxExposure()) : minRp.get(2) + ((maxRp.get(2) - minRp.get(2)) * ((adjustedReturnPeriosd.get(finalI1[0]).get(0) - minRp.get(1)) / (maxRp.get(1) - minRp.get(1)))));
                    }
                }).collect(Collectors.toList());
                return pltLossDatas;
            } else {
                log.info("rpmf must be positive rpmf = {}", rpmf);
                return null;
            }
        } else {
            log.info("plt empty");
            return null;
        }
    }

    private List<PLTLossData> nonLinearEventDrivenAdjustmentOrEventPeriodAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas, boolean periodNoPeriod) {
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            if(peatDatas != null && !peatDatas.isEmpty()) {
                return pltLossDatas.stream().map(pltLossData -> {
                    double lmf = Objects.requireNonNull(peatDatas.stream()
                            .filter(peatData -> (periodNoPeriod && peatData.getEventId() == pltLossData.getEventId() &&
                                    peatData.getSimPeriod() == pltLossData.getSimPeriod()) || (!periodNoPeriod && peatData.getEventId() == pltLossData.getEventId()))
                            .findFirst().orElse(new PEATData(pltLossData.getEventId(), pltLossData.getSimPeriod(), pltLossData.getSeq(), 1))).getLmf();
                    if (lmf > 0) {
                        return new PLTLossData(pltLossData.getSimPeriod(),
                                pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSeq(),
                                cap ? pltLossData.getMaxExposure(): pltLossData.getMaxExposure() * lmf ,
                                cap ? Double.min(lmf * pltLossData.getLoss(), pltLossData.getMaxExposure() ) : lmf * pltLossData.getLoss());
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());
            } else {
                log.info("peat data empty");
                return null;
            }
        } else {
            log.info("plt empty");
            return null;
        }

    }

    public List<PLTLossData> nonLinearEventDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas) {
        return nonLinearEventDrivenAdjustmentOrEventPeriodAdjustment(pltLossDatas, cap, peatDatas, false);
    }

    public List<PLTLossData> nonLinearEventPeriodDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas) {
        return nonLinearEventDrivenAdjustmentOrEventPeriodAdjustment(pltLossDatas, cap, peatDatas, true);
    }

    public List<PLTLossData> linearAdjustement(List<PLTLossData> pltLossDatas, double lmf, boolean cap) {
        if (lmf > 0) {
            if (pltLossDatas != null && !pltLossDatas.isEmpty()) {
                return pltLossDatas.stream().map(pltLossData -> new PLTLossData(pltLossData.getSimPeriod(),
                        pltLossData.getEventId(),
                        pltLossData.getEventDate(),
                        pltLossData.getSeq(),
                        cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * lmf,
                        cap ? Double.min(pltLossData.getLoss() * lmf, pltLossData.getMaxExposure() ) : pltLossData.getLoss() * lmf))
                        .collect(Collectors.toList());
            } else {
                log.info("plt empty");
                return null;
            }
        } else {
            log.info("lmf must be positive lmf={}", lmf);
            return null;

        }
    }
}
