package com.scor.rr.service.adjustement.pltAdjustment;

import com.scor.rr.configuration.UtilsMethod;
import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameter;
import com.scor.rr.domain.dto.EPMetric;
import com.scor.rr.domain.dto.EPMetricPoint;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.domain.enums.StatisticMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class CalculAdjustement implements ICalculAdjustment{

    private static final Logger log = LoggerFactory.getLogger(CalculAdjustement.class);
    private static final double CONSTANTE =100000;

    public static EPMetric getOEPMetric(List<PLTLossData> pltLossDatas){
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            int[] finalI = new int[]{0};
            List<PLTLossData> finalPltLossDatas1 = pltLossDatas;
            pltLossDatas = pltLossDatas.stream().map(pltLossData -> new PLTLossData(pltLossData.getSimPeriod(),pltLossData.getEventId(),pltLossData.getEventDate(),pltLossData.getSeq(),pltLossData.getMaxExposure(), finalPltLossDatas1.stream().filter(pltLossData1 -> pltLossData1.getSimPeriod()==pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).get().getLoss())).filter(UtilsMethode.distinctByKey(PLTLossData::getSimPeriod)).collect(Collectors.toList());
            return new EPMetric(StatisticMetric.OEP,
                    pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossDataVar -> {
                finalI[0]++;
                return new EPMetricPoint(finalI[0] / CONSTANTE, CONSTANTE / finalI[0], pltLossDataVar.getLoss());
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
            pltLossDatas = pltLossDatas.stream().map(pltLossData -> new PLTLossData(pltLossData.getSimPeriod(),pltLossData.getEventId(),pltLossData.getEventDate(),pltLossData.getSeq(),pltLossData.getMaxExposure(), finalPltLossDatas1.stream().filter(pltLossData1 -> pltLossData1.getSimPeriod()==pltLossData.getSimPeriod()).mapToDouble(PLTLossData::getLoss).sum())).filter(UtilsMethode.distinctByKey(PLTLossData::getSimPeriod)).collect(Collectors.toList());
            return new EPMetric(StatisticMetric.OEP,
                    pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(
                    pltLossData -> {
                        finalI[0]++;
                            return new EPMetricPoint(finalI[0] / CONSTANTE,
                                    CONSTANTE / finalI[0] ,
                                    pltLossData.getLoss());
                    }).distinct().collect(Collectors.toList()));
        } else {
            log.info("plt empty");
            return null;
        }
    }

    private Double interpolation(double x,double yMax,double yMin,double xMin,double xMax){
        return yMin+((yMax-yMin)*((x-xMin)/(xMax-xMin)));
    }

    public static List<PLTLossData> oepReturnBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBendings) {
        if (pltLossDatas != null && !pltLossDatas.isEmpty()) {
            if (adjustmentReturnPeriodBendings != null && !adjustmentReturnPeriodBendings.isEmpty()) {
                List<EPMetricPoint> oepMetrics = CalculAdjustement.getOEPMetric(pltLossDatas).getEpMetricPoints();
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

    private static List<PLTLossData> oepAndEEFReturnBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings, String oepEEF) {
        if (pltLossDatas != null && !pltLossDatas.isEmpty()) {
            int[] i = {0};
            int[] finalI = i;
            List<List<Double>> returnPeriosd = pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                finalI[0]++;
                return new ArrayList<Double>() {{
                    add((CalculAdjustement.CONSTANTE / finalI[0]));//return period
                    add(pltLossData.getLoss());//loss
                }};
            }).collect(Collectors.toList());
            if (adjustmentReturnPeriodBandings != null && !adjustmentReturnPeriodBandings.isEmpty()) {
                ReturnPeriodBandingAdjustmentParameter minRpAll = adjustmentReturnPeriodBandings.stream().min(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                i = new int[]{-1};
                int[] finalI1 = i;
                ReturnPeriodBandingAdjustmentParameter maxReturnPeriodBanding = adjustmentReturnPeriodBandings.stream().max(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                return pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                            finalI1[0]++;
                    ReturnPeriodBandingAdjustmentParameter maxRp = adjustmentReturnPeriodBandings.stream().filter(adjustmentReturnPeriodBanding -> adjustmentReturnPeriodBanding.getReturnPeriod() >= returnPeriosd.get(finalI1[0]).get(0)).min(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                    ReturnPeriodBandingAdjustmentParameter minRp = adjustmentReturnPeriodBandings.stream().filter(adjustmentReturnPeriodBanding -> adjustmentReturnPeriodBanding.getReturnPeriod() <= returnPeriosd.get(finalI1[0]).get(0)).max(Comparator.comparingDouble(ReturnPeriodBandingAdjustmentParameter::getReturnPeriod)).orElse(null);
                            if (minRp == null && maxRp != null) {
                                if (oepEEF.equals("OEP")) {
                                    double loss = pltLossDatas.stream().filter(pltLossDatafilter -> pltLossDatafilter.getSimPeriod() == pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).orElse(new PLTLossData()).getLoss();
                                    return new PLTLossData(pltLossData.getSimPeriod(),
                                            pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * ((minRpAll.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (minRpAll.getReturnPeriod()))),
                                            cap ? Double.min(((minRpAll.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (minRpAll.getReturnPeriod()))) * loss, pltLossData.getMaxExposure() ) : (((maxRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (minRpAll.getReturnPeriod()))) * loss));
                                } else {
                                    return new PLTLossData(pltLossData.getSimPeriod(),
                                            pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * ((maxRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (maxRp.getReturnPeriod()))),
                                            cap ? Double.min(((maxRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (maxRp.getReturnPeriod()))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : (((maxRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (maxRp.getReturnPeriod()))) * pltLossData.getLoss()));
                                }
                            } else if (maxRp == null) {
                                return getPltLossData(cap, oepEEF, maxReturnPeriodBanding, pltLossDatas, pltLossData);
                            } else if (maxRp.equals(minRp)) {
                                return getPltLossData(cap, oepEEF, maxRp, pltLossDatas, pltLossData);
                            } else if (returnPeriosd.get(finalI1[0]).get(0).equals(maxRp.getReturnPeriod())) {
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
                                            cap ? pltLossData.getMaxExposure() : minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))) * pltLossData.getMaxExposure(),
                                            cap ? Double.min(((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod())))))  * loss, pltLossData.getMaxExposure() ) : ((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod())))))  * loss);
                                } else {
                                    return new PLTLossData(pltLossData.getSimPeriod(),
                                            pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * (minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod())))),
                                            cap ? Double.min(((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure() ) :((minRp.getAdjustmentFactor() + ((maxRp.getAdjustmentFactor() - minRp.getAdjustmentFactor()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod())))))  * pltLossData.getLoss());
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

    public static List<PLTLossData> oepReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        return oepAndEEFReturnBanding(pltLossDatas, cap, adjustmentReturnPeriodBandings, "OEP");
    }

    public static List<PLTLossData> eefReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandings) {
        return oepAndEEFReturnBanding(pltLossDatas, cap, adjustmentReturnPeriodBandings, "EEF");
    }

    public static List<PLTLossData> eefFrequency(List<PLTLossData> pltLossDatas, boolean cap, double rpmf) {
        if (pltLossDatas != null && !pltLossDatas.isEmpty()) {
            if (rpmf > 0) {
                int[] i = {0};
                int[] finalI = i;
                List<List<Double>> adjustedReturnPeriosd = pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                    finalI[0]++;

                    return new ArrayList<Double>() {{
                        add(((CONSTANTE / finalI[0]) * rpmf));//adjusted return period
                        add((CONSTANTE / finalI[0]));//return period
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
                log.info("rpmf must be positive rpmf={}", rpmf);
                return null;
            }
        } else {
            log.info("plt empty");
            return null;
        }
    }

    private static List<PLTLossData> nonLinearEventDrivenAdjustmentOrEventPeriodAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas, boolean periodNoPeriod) {
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

    public static List<PLTLossData> nonLinearEventDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas) {
        return nonLinearEventDrivenAdjustmentOrEventPeriodAdjustment(pltLossDatas, cap, peatDatas, false);
    }

    public static List<PLTLossData> nonLinearEventPeriodDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas) {
        return nonLinearEventDrivenAdjustmentOrEventPeriodAdjustment(pltLossDatas, cap, peatDatas, true);
    }

    public static List<PLTLossData> linearAdjustement(List<PLTLossData> pltLossDatas, double lmf, boolean cap) {
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
