package com.scor.rr.importBatch.processing.statistics;

//import com.scor.almf.cdm.domain.cat.*;
//
//import com.scor.rr.domain.entities.cat.CATAnalysis;
//import com.scor.rr.domain.entities.cat.CATAnalysisModelResults;
//import com.scor.rr.domain.utils.cat.ModellingResult;
//import com.scor.rr.domain.utils.plt.EPCurve;
//import com.scor.rr.importBatch.domain.EPMetric;
//import com.scor.rr.importBatch.domain.PeriodBasis;
//import com.scor.rr.importBatch.domain.SystemReturnPeriod;
//import com.scor.rr.importBatch.processing.batch.BeanioWriter;
//import com.scor.rr.importBatch.processing.domain.FWData;
//import com.scor.rr.importBatch.processing.domain.FWValue;
//import com.scor.rr.importBatch.processing.mapping.MappingHandler;
//import com.scor.rr.importBatch.repository.SystemReturnPeriodRepository;
//import com.scor.rr.repository.cat.CATAnalysisModelResultsRepository;
//import com.scor.rr.repository.cat.CATAnalysisRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Map;

/**
 * Created by U002629 on 08/04/2015.
 */
public class FWValuesWriter /*implements FWWriter*/ {

//    private static final Logger log= LoggerFactory.getLogger(FWValuesWriter.class);
//    private static final String END = "End";
//    private static final String START = "Start";
//
//    private BeanioWriter writer;
//    private SystemReturnPeriodRepository systemReturnPeriodRepository;
//    private CATAnalysisRepository catAnalysisRepository;
//    private CATAnalysisModelResultsRepository catAnalysisModelResultsRepository;
//    protected MappingHandler mappingHandler;
//    private FWData fwData;
//
//    public FWValuesWriter() {
//    }
//
//    public void setFwData(FWData fwData) {
//        this.fwData = fwData;
//    }
//
//    public void setWriter(BeanioWriter writer) {
//        this.writer = writer;
//    }
//
//    public void setSystemReturnPeriodRepository(SystemReturnPeriodRepository systemReturnPeriodRepository) {
//        this.systemReturnPeriodRepository = systemReturnPeriodRepository;
//    }
//
//    public void setCatAnalysisRepository(CATAnalysisRepository catAnalysisRepository) {
//        this.catAnalysisRepository = catAnalysisRepository;
//    }
//
//    public void setCatAnalysisModelResultsRepository(CATAnalysisModelResultsRepository catAnalysisModelResultsRepository) {
//        this.catAnalysisModelResultsRepository = catAnalysisModelResultsRepository;
//    }
//
//    public void setMappingHandler(MappingHandler mappingHandler) {
//        this.mappingHandler = mappingHandler;
//    }
//
//    @Override
//    public Boolean writeValues(){
//        CATAnalysisRequest request = fwData.myRequest();
//        String catReqId = request.getId();
//        final List<SystemReturnPeriod> fwReturnPeriods = systemReturnPeriodRepository.findBySystemOrderByReturnPeriod("FW");
//
//
//        final List<CATAnalysisModelResults> byCatAnalysisId = catAnalysisModelResultsRepository.findByCatAnalysisIdAndPeriodBasisId(CATAnalysis.buildId(catReqId), PeriodBasis.FULL);
//        for (CATAnalysisModelResults modelResults : byCatAnalysisId) {
//            Integer divisionId = modelResults.getDivisionNumber();
//            writer.createWriter(catReqId + "_" + divisionId.toString()+".vls");
//            writer.writeHeader();
//            String divCcurrency = request.getuWanalysis().getDivisions().get(divisionId).getCurrency().getCode();
//
//            modelResults.getFinancialPerspectiveStats();
//            Map<String, ModellingResult> modellingResultsByRegionPeril = modelResults.getModellingResultsByRegionPeril();
//            for (String rp : modellingResultsByRegionPeril.keySet()) {
//                ModellingResult modellingResult = modellingResultsByRegionPeril.get(rp);
//
//                EPCurve epCurveGU = modellingResult.getElt().getStatsEpCurvesByEpType().get(EPMetric.OEP);
//                EPCurve epCurveGR = modellingResult.getPurePLT().getEpCurvesByType().get(EPMetric.OEP);
//                String sourceAnalysisCurrency;
//                try {
//                    sourceAnalysisCurrency = modellingResult.getElt().getEltSourceMetadata().getSourceAnalysisCurrency();
//                } catch (Throwable e) {
//                    log.warn("cannot retrieve the currency from elt source metadata");
//                    sourceAnalysisCurrency=divCcurrency;
//                }
//                if(epCurveGU!=null&&epCurveGR!=null) {
//                    Map<Integer, Double> lossAmountsByReturnPeriodGU = epCurveGU.getLossAmountsByReturnPeriod();
//                    Map<Integer, Double> lossAmountsByReturnPeriodGR = epCurveGR.getLossAmountsByReturnPeriod();
//                    for (SystemReturnPeriod fwReturnPeriod : fwReturnPeriods) {
//                        Integer returnPeriod = fwReturnPeriod.getReturnPeriod();
//                        Double lossAmountGU = lossAmountsByReturnPeriodGU.get(returnPeriod);
//                        Double lossAmountGross = lossAmountsByReturnPeriodGR.get(returnPeriod);
//                        writer.write(new FWValue(catReqId, divisionId, rp, returnPeriod, sourceAnalysisCurrency, lossAmountGU, lossAmountGross, END));
//                    }
//                }
//            }
//
//            final CATAnalysisModelResults byCatAnalysisIdAndDivisionNumberAndPeriodBasisId = catAnalysisModelResultsRepository.findByCatAnalysisIdAndDivisionNumberAndPeriodBasisId(CATAnalysis.buildId(catReqId), divisionId, PeriodBasis.Y1);
//            if (byCatAnalysisIdAndDivisionNumberAndPeriodBasisId != null) {
//                Map<String, ModellingResult> modellingResultsByRegionPerily1 = byCatAnalysisIdAndDivisionNumberAndPeriodBasisId.getModellingResultsByRegionPeril();
//                for (String rp : modellingResultsByRegionPeril.keySet()) {
//                    ModellingResult modellingResult = modellingResultsByRegionPerily1.get(rp);
//
//                    EPCurve epCurveGU = modellingResult.getElt().getStatsEpCurvesByEpType().get(EPMetric.OEP);
//                    EPCurve epCurveGR = modellingResult.getPurePLT().getEpCurvesByType().get(EPMetric.OEP);
//                    String sourceAnalysisCurrency;
//                    try {
//                        sourceAnalysisCurrency = modellingResult.getElt().getEltSourceMetadata().getSourceAnalysisCurrency();
//                    } catch (Throwable e) {
//                        log.warn("cannot retrieve the currency from elt source metadata");
//                        sourceAnalysisCurrency=divCcurrency;
//                    }
//                    if(epCurveGU!=null&&epCurveGR!=null) {
//                        Map<Integer, Double> lossAmountsByReturnPeriodGU = epCurveGU.getLossAmountsByReturnPeriod();
//                        Map<Integer, Double> lossAmountsByReturnPeriodGR = epCurveGR.getLossAmountsByReturnPeriod();
//                        for (SystemReturnPeriod fwReturnPeriod : fwReturnPeriods) {
//                            Integer returnPeriod = fwReturnPeriod.getReturnPeriod();
//                            Double lossAmountGU = lossAmountsByReturnPeriodGU.get(returnPeriod);
//                            Double lossAmountGross = lossAmountsByReturnPeriodGR.get(returnPeriod);
//                            writer.write(new FWValue(catReqId, divisionId, rp, returnPeriod, sourceAnalysisCurrency, lossAmountGU, lossAmountGross, START));
//                        }
//                    }
//                }
//            }
//            writer.end();
//        }
//
//        return true;
//    }

}
