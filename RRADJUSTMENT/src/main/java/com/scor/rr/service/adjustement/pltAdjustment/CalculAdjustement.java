package com.scor.rr.service.adjustement.pltAdjustment;

import com.scor.rr.domain.dto.OEPMetric;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.stream.Collectors;


public class CalculAdjustement implements ICalculAdjustment{

    private static final Logger log = LoggerFactory.getLogger(CalculAdjustement.class);
    final double CONSTANTE =100000;
    public List<OEPMetric> getOEPMetric(List<PLTLossData> pltLossData){
        if(pltLossData != null && !pltLossData.isEmpty()) {
            int[] finalI = new int[]{0};
            return pltLossData.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossDataVar -> {
                finalI[0]++;
                return new OEPMetric(finalI[0] / CONSTANTE, CONSTANTE / finalI[0], pltLossDataVar.getLoss());
            }).collect(Collectors.toList());
        } else {
            log.info("plt empty");
            return null;
        }
    }

    private Double interpolation(double x,double yMax,double yMin,double xMin,double xMax){
        return yMin+((yMax-yMin)*((x-xMin)/(xMax-xMin)));
    }

    private List<PLTLossData> oepAndEEFReturnBanding(List<PLTLossData> pltLossDatas, boolean cap, List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings, double periodConstante, String oepEEF) {
        if (pltLossDatas != null && !pltLossDatas.isEmpty()) {
            int[] i = {0};
            int[] finalI = i;
            List<List<Double>> returnPeriosd = pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                finalI[0]++;
                return new ArrayList<Double>() {{
                    add((periodConstante / finalI[0]));//return period
                    add(pltLossData.getLoss());//loss
                }};
            }).collect(Collectors.toList());
            if (adjustmentReturnPeriodBendings != null && !adjustmentReturnPeriodBendings.isEmpty()) {
                AdjustmentReturnPeriodBending minRpAll = adjustmentReturnPeriodBendings.stream().min(Comparator.comparingDouble(AdjustmentReturnPeriodBending::getReturnPeriod)).orElse(null);
                i = new int[]{-1};
                int[] finalI1 = i;
                AdjustmentReturnPeriodBending maxReturnPeriodBanding = adjustmentReturnPeriodBendings.stream().max(Comparator.comparingDouble(AdjustmentReturnPeriodBending::getReturnPeriod)).orElse(null);
                return pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                            finalI1[0]++;
                            AdjustmentReturnPeriodBending maxRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() >= returnPeriosd.get(finalI1[0]).get(0)).min(Comparator.comparingDouble(AdjustmentReturnPeriodBending::getReturnPeriod)).orElse(null);
                            AdjustmentReturnPeriodBending minRp = adjustmentReturnPeriodBendings.stream().filter(adjustmentReturnPeriodBending -> adjustmentReturnPeriodBending.getReturnPeriod() <= returnPeriosd.get(finalI1[0]).get(0)).max(Comparator.comparingDouble(AdjustmentReturnPeriodBending::getReturnPeriod)).orElse(null);
                            if (minRp == null && maxRp != null) {
                                if (oepEEF.equals("OEP")) {
                                    double loss = pltLossDatas.stream().filter(pltLossDatafilter -> pltLossDatafilter.getSimPeriod() == pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).orElse(new PLTLossData()).getLoss();
                                    return new PLTLossData(pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSimPeriod(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * ((minRpAll.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (minRpAll.getReturnPeriod()))),
                                            cap ? Double.min(((minRpAll.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (minRpAll.getReturnPeriod()))) * loss, pltLossData.getMaxExposure() ) : (((maxRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (minRpAll.getReturnPeriod()))) * loss));
                                } else {
                                    return new PLTLossData(pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSimPeriod(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * ((maxRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (maxRp.getReturnPeriod()))),
                                            cap ? Double.min(((maxRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (maxRp.getReturnPeriod()))) * pltLossData.getLoss(), pltLossData.getMaxExposure()) : (((maxRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0)) / (maxRp.getReturnPeriod()))) * pltLossData.getLoss()));
                                }
                            } else if (maxRp == null) {
                                return getPltLossData(cap, oepEEF, maxReturnPeriodBanding, pltLossDatas, pltLossData);
                            } else if (maxRp.equals(minRp)) {
                                return getPltLossData(cap, oepEEF, maxRp, pltLossDatas, pltLossData);
                            } else if (returnPeriosd.get(finalI1[0]).get(0).equals(maxRp.getReturnPeriod())) {
                                if (oepEEF.equals("OEP")) {
                                    double loss = pltLossDatas.stream().filter(pltLossDatafilter -> pltLossDatafilter.getSimPeriod() == pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).orElse(new PLTLossData()).getLoss();
                                    return new PLTLossData(pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSimPeriod(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxRp.getLmf(),
                                            cap ? Double.min(maxRp.getLmf() * loss, pltLossData.getMaxExposure()) : maxRp.getLmf() * loss);
                                } else {
                                    return new PLTLossData(pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSimPeriod(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure()*maxRp.getLmf(),
                                            cap ? Double.min(maxRp.getLmf() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : maxRp.getLmf() * pltLossData.getLoss());

                                }
                            } else {
                                if (oepEEF.equals("OEP")) {
                                    double loss = pltLossDatas.stream().filter(pltLossDatafilter -> pltLossDatafilter.getSimPeriod() == pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).orElse(new PLTLossData()).getLoss();
                                    return new PLTLossData(pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSimPeriod(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : minRp.getLmf() + ((maxRp.getLmf() - minRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))) * pltLossData.getMaxExposure(),
                                            cap ? Double.min((minRp.getLmf() + ((maxRp.getLmf() - minRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod())))) * loss, pltLossData.getMaxExposure() * (minRp.getLmf() + ((maxRp.getLmf() - minRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) : ((minRp.getLmf() + ((maxRp.getLmf() - minRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getLmf()) / (maxRp.getLmf() - minRp.getLmf())))) * loss));
                                } else {
                                    return new PLTLossData(pltLossData.getEventId(),
                                            pltLossData.getEventDate(),
                                            pltLossData.getSimPeriod(),
                                            pltLossData.getSeq(),
                                            cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * (minRp.getLmf() + ((maxRp.getLmf() - minRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod())))),
                                            cap ? Double.min(((minRp.getLmf() + ((maxRp.getLmf() - minRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getReturnPeriod()) / (maxRp.getReturnPeriod() - minRp.getReturnPeriod()))))) * pltLossData.getLoss(), pltLossData.getMaxExposure() ) : (minRp.getLmf() + ((maxRp.getLmf() - minRp.getLmf()) * ((returnPeriosd.get(finalI1[0]).get(0) - minRp.getLmf()) / (maxRp.getLmf() - minRp.getLmf()))) * pltLossData.getLoss()));
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

    private PLTLossData getPltLossData(boolean cap, String oepEEF, AdjustmentReturnPeriodBending maxReturnPeriodBanding, List<PLTLossData> finalPltLossDatas, PLTLossData pltLossData) {
        if (oepEEF.equals("OEP")) {
            double loss = finalPltLossDatas.stream().filter(pltLossDatafilter -> pltLossDatafilter.getSimPeriod() == pltLossData.getSimPeriod()).max(Comparator.comparing(PLTLossData::getLoss)).get().getLoss();
            return new PLTLossData(pltLossData.getEventId(),
                    pltLossData.getEventDate(),
                    pltLossData.getSimPeriod(),
                    pltLossData.getSeq(),
                    cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxReturnPeriodBanding.getLmf(),
                    cap ? Double.min(maxReturnPeriodBanding.getLmf() * loss, pltLossData.getMaxExposure()) : maxReturnPeriodBanding.getLmf() * loss);
        } else {
            return new PLTLossData(pltLossData.getEventId(),
                    pltLossData.getEventDate(),
                    pltLossData.getSimPeriod(),
                    pltLossData.getSeq(),
                    cap ? pltLossData.getMaxExposure() : pltLossData.getMaxExposure() * maxReturnPeriodBanding.getLmf(),
                    cap ? Double.min(maxReturnPeriodBanding.getLmf() * pltLossData.getLoss(), pltLossData.getMaxExposure()) : maxReturnPeriodBanding.getLmf() * pltLossData.getLoss());
        }
    }

    public List<PLTLossData> oepReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings) {
        return this.oepAndEEFReturnBanding(pltLossDatas, cap, adjustmentReturnPeriodBendings, CONSTANTE, "OEP");
    }

    public List<PLTLossData> eefReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings) {
        return this.oepAndEEFReturnBanding(pltLossDatas, cap, adjustmentReturnPeriodBendings, CONSTANTE, "EEF");
    }

    public List<PLTLossData> eefFrequency(List<PLTLossData> pltLossDatas, boolean cap, double rpmf) {
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
                        (double) 0,
                        0,
                        (double) 0, (double) 0)).getLoss();
                i = new int[]{-1};
                int[] finalI1 = i;
                pltLossDatas = pltLossDatas.stream().sorted(Comparator.comparing(PLTLossData::getLoss).reversed()).map(pltLossData -> {
                    finalI1[0]++;
                    List<Double> maxRp = adjustedReturnPeriosd.stream().filter(doubles -> doubles.get(1) >= adjustedReturnPeriosd.get(finalI1[0]).get(0)).min(Comparator.comparingDouble(o -> o.get(1))).orElse(null);
                    List<Double> minRp = adjustedReturnPeriosd.stream().filter(doubles -> doubles.get(1) <= adjustedReturnPeriosd.get(finalI1[0]).get(0)).max(Comparator.comparingDouble(o -> o.get(1))).orElse(null);

                    if (minRp == null) {
                        return new PLTLossData(pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSimPeriod(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                0);
                    }

                    if (maxRp == null) {
                        return new PLTLossData(pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSimPeriod(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                maxLoss);
                    } else if (maxRp.equals(minRp)) {
                        return new PLTLossData(pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSimPeriod(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                maxRp.get(2));
                    } else if (adjustedReturnPeriosd.get(finalI1[0]).get(0).equals(maxRp.get(1))) {
                        return new PLTLossData(pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSimPeriod(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                maxRp.get(2));
                    } else {
                        return new PLTLossData(pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSimPeriod(),
                                pltLossData.getSeq(),
                                pltLossData.getMaxExposure(),
                                cap ? Double.min(minRp.get(2) + ((maxRp.get(2) - minRp.get(2)) * ((adjustedReturnPeriosd.get(finalI1[0]).get(0) - minRp.get(1)) / (maxRp.get(1) - minRp.get(1)))), pltLossData.getMaxExposure()) : minRp.get(2) + ((maxRp.get(2) - minRp.get(2)) * ((adjustedReturnPeriosd.get(finalI1[0]).get(0) - minRp.get(2)) / (maxRp.get(2) - minRp.get(2)))));
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

    private List<PLTLossData> nonLineaireEventDrivenAdjustmentOrEventPeriodAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas, boolean periodNoPeriod) {
        if(pltLossDatas != null && !pltLossDatas.isEmpty()) {
            if(peatDatas != null && !peatDatas.isEmpty()) {
                return pltLossDatas.stream().map(pltLossData -> {
                    double lmf = Objects.requireNonNull(peatDatas.stream()
                            .filter(peatData -> (periodNoPeriod && peatData.getEventId() == pltLossData.getEventId() &&
                                    peatData.getSimPeriod() == pltLossData.getSimPeriod()) || (!periodNoPeriod && peatData.getEventId() == pltLossData.getEventId()))
                            .findFirst().orElse(new PEATData(pltLossData.getEventId(), pltLossData.getSimPeriod(), pltLossData.getSeq(), 1))).getLmf();
                    if (lmf > 0) {
                        return new PLTLossData(pltLossData.getEventId(),
                                pltLossData.getEventDate(),
                                pltLossData.getSimPeriod(),
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

    public List<PLTLossData> nonLineaireEventDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas) {
        return nonLineaireEventDrivenAdjustmentOrEventPeriodAdjustment(pltLossDatas, cap, peatDatas, false);
    }

    public List<PLTLossData> nonLineaireEventPeriodDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas) {
        return nonLineaireEventDrivenAdjustmentOrEventPeriodAdjustment(pltLossDatas, cap, peatDatas, true);
    }

    public List<PLTLossData> lineaireAdjustement(List<PLTLossData> pltLossDatas, double lmf, boolean cap) {
        if (lmf > 0) {
            if (pltLossDatas != null && !pltLossDatas.isEmpty()) {
                return pltLossDatas.stream().map(pltLossData -> new PLTLossData(pltLossData.getEventId(),
                        pltLossData.getEventDate(),
                        pltLossData.getSimPeriod(),
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
