package com.scor.rr.rest;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.AnalysisHeader;
import com.scor.rr.domain.dto.SourceResultDto;
import com.scor.rr.service.RmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rms")
public class RmsRessource {

    private final Logger logger = LoggerFactory.getLogger(RmsRessource.class);

    @Autowired
    RmsService rmsService;


    @GetMapping("listAvailableDataSources")
    public ResponseEntity<?> listAvailableDataSources(@RequestParam String instanceId) {

        this.logger.debug("controller starts getting dataSource ...");

        List<DataSource> dataSourcs = rmsService.listAvailableDataSources(instanceId);

        return ResponseEntity.ok(dataSourcs.subList(1,20));
    }

    @GetMapping("listRdmAnalysisBasic")
    public ResponseEntity<?> listRdmAnalysisBasic(@RequestParam String instanceId, @RequestParam(value = "id") Long id, @RequestParam(value = "name") String name) {

        this.logger.debug("start getting listRdmAnalysisBasic ...");

        List<RdmAnalysisBasic> rdmAnalysisBasic = rmsService.listRdmAnalysisBasic(instanceId, id, name);

        return ResponseEntity.ok(rdmAnalysisBasic);
    }

    @GetMapping("listEdmPortfolioBasic")
    public ResponseEntity<?> listEdmPortfolioBasic(@RequestParam String instanceId, @RequestParam(value = "id") Long id, @RequestParam(value = "name") String name) {

        this.logger.debug("start getting listEdmPortfolioBasic ...");

        List<EdmPortfolioBasic> edmPortfolioBasics = rmsService.listEdmPortfolioBasic(instanceId, id, name);

        return ResponseEntity.ok(edmPortfolioBasics);
    }

    @GetMapping("listRdmAnalysis")
    public ResponseEntity<?> listRdmAnalysis(@RequestParam String instanceId,
                                             @RequestParam(value = "id") Long id, @RequestParam(value = "name") String name,
                                             @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {

        this.logger.debug("start getting listRdmAnalysis ...");

        List<RdmAnalysis> rdmAnalyses = rmsService.listRdmAnalysis(instanceId, id, name, analysisIdList);

        return ResponseEntity.ok(rdmAnalyses);
    }

    @GetMapping("listEdmPortfolio")
    public ResponseEntity<?> listEdmPortfolio(@RequestParam String instanceId, @RequestParam(value = "id") Long id, @RequestParam(value = "name") String name,
                                              @RequestParam(value = "portfolioList", required = false) List<String> portfolioList) {

        this.logger.debug("start getting listEdmPortfolio ...");

        List<EdmPortfolio> edmPortfolios = rmsService.listEdmPortfolio(instanceId, id, name, portfolioList);
        return ResponseEntity.ok(edmPortfolios);
    }

    @GetMapping("listRdmAnalysisEpCurves")
    public ResponseEntity<?> listRdmAllAnalysisEpCurves(@RequestParam String instanceId,
                                                        @RequestParam(value = "id") Long id, @RequestParam(value = "name") String name,
                                                        @RequestParam(value = "epPoints") int epPoints,
                                                        @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList,
                                                        @RequestParam(value = "finPerspList", required = false) List<String> finPerspList) {
        this.logger.debug("start getting listRdmAnalysisEpCurves ...");

        List<RdmAnalysisEpCurves> rdmAnalysisEpCurves = rmsService.listRdmAllAnalysisEpCurves(instanceId, id, name, epPoints, analysisIdList, finPerspList);

        return ResponseEntity.ok(rdmAnalysisEpCurves);
    }

    @GetMapping("RdmAllAnalysisSummaryStats")
    public ResponseEntity<?> getRdmAllAnalysisSummaryStats(@RequestParam String instanceId, @RequestParam(value = "id") Long id, @RequestParam(value = "name") String name,
                                                           @RequestParam(value = "finPerspList", required = false) List<String> finPerspList,
                                                           @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {
        this.logger.debug("start getting RdmAllAnalysisSummaryStats ...");

        List<RdmAllAnalysisSummaryStats> rdmAllAnalysisSummaryStats = rmsService.getRdmAllAnalysisSummaryStats(instanceId, id, name, finPerspList, analysisIdList);

        return ResponseEntity.ok(rdmAllAnalysisSummaryStats);

    }

    @GetMapping("AnalysisEpCurves")
    public ResponseEntity<?> getAnalysisEpCurves(@RequestParam String instanceId,
                                                 @RequestParam(value = "rdmID") Long rdmID,
                                                 @RequestParam(value = "rdmName") String rdmName,
                                                 @RequestParam(value = "analysisId") Long analysisId,
                                                 @RequestParam(value = "finPerspCode") String finPerspCode,
                                                 @RequestParam(value = "treatyLabelId", required = false) Integer treatyLabelId) {
        this.logger.debug("start getting AnalysisEpCurves ...");

        List<AnalysisEpCurves> rdmAllAnalysisSummaryStats = rmsService.getAnalysisEpCurves(instanceId, rdmID, rdmName, analysisId, finPerspCode, treatyLabelId);

        return ResponseEntity.ok(rdmAllAnalysisSummaryStats);
    }

    @GetMapping("AnalysisSummaryStats")
    public ResponseEntity<?> getAnalysisSummaryStats(@RequestParam String instanceId,
                                                     @RequestParam(value = "rdmId") Long rdmId,
                                                     @RequestParam(value = "rdmName") String rdmName,
                                                     @RequestParam(value = "analysisId") Long analysisId,
                                                     @RequestParam(value = "finPerspCode") String finPerspCode,
                                                     @RequestParam(value = "treatyLabelId", required = false) Integer treatyLabelId) {

        this.logger.debug("start getting AnalysisEpCurves ...");
        List<AnalysisSummaryStats> analysisSummaryStats = rmsService.getAnalysisSummaryStats(instanceId, rdmId, rdmName, analysisId, finPerspCode, treatyLabelId);
        return ResponseEntity.ok(analysisSummaryStats);
    }

    @GetMapping("RdmAllAnalysisProfileRegions")
    public ResponseEntity<?> getRdmAllAnalysisProfileRegions(
            @RequestParam String instanceId,
            @RequestParam(value = "rdmId") Long rdmId,
            @RequestParam(value = "rdmName") String rdmName,
            @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {

        this.logger.debug("start getting RdmAllAnalysisProfileRegions ...");
        List<RdmAllAnalysisProfileRegions> rdmAllAnalysisProfileRegions = rmsService.getRdmAllAnalysisProfileRegions(instanceId, rdmId, rdmName, analysisIdList);
        return ResponseEntity.ok(rdmAllAnalysisProfileRegions);

    }

    @GetMapping("AnalysisElt")
    public ResponseEntity<?> getAnalysisElt(@RequestParam(value = "rdmId") Long rdmId,
                                            @RequestParam(value = "rdmName") String rdmName,
                                            @RequestParam(value = "analysisId") Long analysisId,
                                            @RequestParam(value = "finPerspCode") String finPerspCode,
                                            @RequestParam(value = "treatyLabelId", required = false) Integer treatyLabelId) {
        this.logger.debug("start getting AnalysisElt ...");
        //List<RlEltLoss> rlEltLoss = rmsService.getAnalysisElt(0,rdmId, rdmName, analysisId, finPerspCode, treatyLabelId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("EdmAllPortfolioAnalysisRegions")
    public ResponseEntity<?> getEdmAllPortfolioAnalysisRegions(
            @RequestParam String instanceId,
            @RequestParam(value = "edmId") Long edmId,
            @RequestParam(value = "edmName") String edmName,
            @RequestParam(value = "ccy") String ccy) {
        this.logger.debug("start getting EdmAllPortfolioAnalysisRegions ...");
        List<EdmAllPortfolioAnalysisRegions> edmAllPortfolioAnalysisRegions = rmsService.getEdmAllPortfolioAnalysisRegions(instanceId, edmId, edmName, ccy);
        return ResponseEntity.ok(edmAllPortfolioAnalysisRegions);
    }

    @GetMapping("RdmAllAnalysisTreatyStructure")
    public ResponseEntity<?> getRdmAllAnalysisTreatyStructure(
            @RequestParam String instanceId,
            @RequestParam(value = "rdmId") Long rdmId,
            @RequestParam(value = "rdmName") String rdmname,
            @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {
        List<RdmAllAnalysisTreatyStructure> rdmAllAnalysisTreatyStructure = rmsService.getRdmAllAnalysisTreatyStructure(instanceId, rdmId, rdmname, analysisIdList);
        return ResponseEntity.ok(rdmAllAnalysisTreatyStructure);
    }

    @GetMapping("RdmAllAnalysisMultiRegionPerils")
    public ResponseEntity<?> getRdmAllAnalysisMultiRegionPerils(
            @RequestParam String instanceId,
            @RequestParam(value = "rdmId") Long rdmId,
            @RequestParam(value = "rdmName") String rdmName,
            @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {

        List<RdmAllAnalysisMultiRegionPerils> rdmAllAnalysisMultiRegionPerils = rmsService.getRdmAllAnalysisMultiRegionPerils(instanceId, rdmId, rdmName, analysisIdList);

        return ResponseEntity.ok(rdmAllAnalysisMultiRegionPerils);
    }

    @GetMapping("RmsExchangeRates")
    public ResponseEntity<?> getRmsExchangeRates(@RequestParam String instanceId, @RequestParam(value = "ccy") List<String> ccy) {
        List<RmsExchangeRate> rmsExchangeRates = rmsService.getRmsExchangeRates(instanceId, ccy);
        return ResponseEntity.ok(rmsExchangeRates);
    }

    @GetMapping("CChBaseCcy")
    public ResponseEntity<?> getCChBaseCcy(@RequestParam String instanceId) {
        List<CChkBaseCcy> cChBaseCcy = rmsService.getCChBaseCcy(instanceId);
        return ResponseEntity.ok(cChBaseCcy);
    }

    @GetMapping("CChkBaseCcyFxRate")
    public ResponseEntity<?> getCChkBaseCcyFxRate(@RequestParam String instanceId) {
        List<CChkBaseCcyFxRate> cChkBaseCcyFxRate = rmsService.getCChkBaseCcyFxRate(instanceId);
        return ResponseEntity.ok(cChkBaseCcyFxRate);
    }

    @GetMapping("GetAnalysisModellingOptionSettings")
    public String getAnalysisModellingOptionSettings(
            @RequestParam String instanceId,
            @RequestParam(value = "rdmId") Long rdmId,
            @RequestParam(value = "rdmName") String rdmName,
            @RequestParam(value = "analysisId") Long analysisId) {
        return rmsService.getAnalysisModellingOptionSettings(instanceId, rdmId, rdmName, analysisId);
    }


}
