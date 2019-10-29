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

    @Autowired
    RmsService rmsService;

    private final Logger logger = LoggerFactory.getLogger(RmsRessource.class);

    @GetMapping("listAvailableDataSources")
    public ResponseEntity<?> listAvailableDataSources() {

        this.logger.debug("controller starts getting dataSource ...");

        List<DataSource> dataSourcs = rmsService.listAvailableDataSources();

        return ResponseEntity.ok(dataSourcs);
    }


    @PostMapping("add-edm-rdm")
    public ResponseEntity<?> addEmdRdm(@RequestBody List<DataSource> dataSources, @RequestParam Long projectId, @RequestParam Long instanceId, @RequestParam String instanceName) {
        rmsService.addEdmRdms(dataSources, projectId, instanceId, instanceName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("analysis-detail-scan")
    public ResponseEntity<?> analysisDetailScan(@RequestBody List<AnalysisHeader> rlAnalysisList, @RequestParam Integer projectId) {
        rmsService.scanAnalysisDetail(rlAnalysisList, projectId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("listRdmAnalysisBasic")
    public ResponseEntity<?> listRdmAnalysisBasic(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name) {

        this.logger.debug("start getting listRdmAnalysisBasic ...");

        List<RdmAnalysisBasic> rdmAnalysisBasic = rmsService.listRdmAnalysisBasic(id, name);

        return ResponseEntity.ok(rdmAnalysisBasic);
    }

    @GetMapping("listEdmPortfolioBasic")
    public ResponseEntity<?> listEdmPortfolioBasic(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name) {

        this.logger.debug("start getting listEdmPortfolioBasic ...");

        List<EdmPortfolioBasic> edmPortfolioBasics = rmsService.listEdmPortfolioBasic(id, name);

        return ResponseEntity.ok(edmPortfolioBasics);
    }

    @GetMapping("listRdmAnalysis")
    public ResponseEntity<?> listRdmAnalysis(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name,
                                             @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {

        this.logger.debug("start getting listRdmAnalysis ...");

        List<RdmAnalysis> rdmAnalyses = rmsService.listRdmAnalysis(id, name, analysisIdList);

        return ResponseEntity.ok(rdmAnalyses);
    }

    @GetMapping("listEdmPortfolio")
    public ResponseEntity<?> listEdmPortfolio(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name,
                                              @RequestParam(value = "portfolioList", required = false) List<String> portfolioList) {

        this.logger.debug("start getting listEdmPortfolio ...");

        List<EdmPortfolio> edmPortfolios = rmsService.listEdmPortfolio(id, name, portfolioList);
        return ResponseEntity.ok(edmPortfolios);
    }

    @GetMapping("listRdmAnalysisEpCurves")
    public ResponseEntity<?> listRdmAllAnalysisEpCurves(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name,
                                                        @RequestParam(value = "epPoints") int epPoints,
                                                        @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList,
                                                        @RequestParam(value = "finPerspList", required = false) List<String> finPerspList) {
        this.logger.debug("start getting listRdmAnalysisEpCurves ...");

        List<RdmAnalysisEpCurves> rdmAnalysisEpCurves = rmsService.listRdmAllAnalysisEpCurves(id, name, epPoints, analysisIdList, finPerspList);

        return ResponseEntity.ok(rdmAnalysisEpCurves);
    }

    @GetMapping("RdmAllAnalysisSummaryStats")
    public ResponseEntity<?> getRdmAllAnalysisSummaryStats(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name,
                                                           @RequestParam(value = "finPerspList", required = false) List<String> finPerspList,
                                                           @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {
        this.logger.debug("start getting RdmAllAnalysisSummaryStats ...");

        List<RdmAllAnalysisSummaryStats> rdmAllAnalysisSummaryStats = rmsService.getRdmAllAnalysisSummaryStats(id, name, finPerspList, analysisIdList);

        return ResponseEntity.ok(rdmAllAnalysisSummaryStats);

    }

    @GetMapping("AnalysisEpCurves")
    public ResponseEntity<?> getAnalysisEpCurves(@RequestParam(value = "rdmID") Long rdmID,
                                                 @RequestParam(value = "rdmName") String rdmName,
                                                 @RequestParam(value = "analysisId") Long analysisId,
                                                 @RequestParam(value = "finPerspCode") String finPerspCode,
                                                 @RequestParam(value = "treatyLabelId", required = false) Integer treatyLabelId) {
        this.logger.debug("start getting AnalysisEpCurves ...");

        List<AnalysisEpCurves> rdmAllAnalysisSummaryStats = rmsService.getAnalysisEpCurves(rdmID, rdmName, analysisId, finPerspCode, treatyLabelId);

        return ResponseEntity.ok(rdmAllAnalysisSummaryStats);
    }

    @GetMapping("AnalysisSummaryStats")
    public ResponseEntity<?> getAnalysisSummaryStats(@RequestParam(value = "rdmId") Long rdmId,
                                                     @RequestParam(value = "rdmName") String rdmName,
                                                     @RequestParam(value = "analysisId") Long analysisId,
                                                     @RequestParam(value = "finPerspCode") String finPerspCode,
                                                     @RequestParam(value = "treatyLabelId", required = false) Integer treatyLabelId) {

        this.logger.debug("start getting AnalysisEpCurves ...");
        List<AnalysisSummaryStats> analysisSummaryStats = rmsService.getAnalysisSummaryStats(rdmId, rdmName, analysisId, finPerspCode, treatyLabelId);
        return ResponseEntity.ok(analysisSummaryStats);
    }

    @GetMapping("RdmAllAnalysisProfileRegions")
    public ResponseEntity<?> getRdmAllAnalysisProfileRegions(@RequestParam(value = "rdmId") Long rdmId,
                                                             @RequestParam(value = "rdmName") String rdmName,
                                                             @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {

        this.logger.debug("start getting RdmAllAnalysisProfileRegions ...");
        List<RdmAllAnalysisProfileRegions> rdmAllAnalysisProfileRegions = rmsService.getRdmAllAnalysisProfileRegions(rdmId, rdmName, analysisIdList);
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
    public ResponseEntity<?> getEdmAllPortfolioAnalysisRegions(@RequestParam(value = "edmId") Long edmId,
                                                               @RequestParam(value = "edmName") String edmName,
                                                               @RequestParam(value = "ccy") String ccy) {
        this.logger.debug("start getting EdmAllPortfolioAnalysisRegions ...");
        List<EdmAllPortfolioAnalysisRegions> edmAllPortfolioAnalysisRegions = rmsService.getEdmAllPortfolioAnalysisRegions(edmId, edmName, ccy);
        return ResponseEntity.ok(edmAllPortfolioAnalysisRegions);
    }

    @GetMapping("RdmAllAnalysisTreatyStructure")
    public ResponseEntity<?> getRdmAllAnalysisTreatyStructure(@RequestParam(value = "rdmId") Long rdmId,
                                                              @RequestParam(value = "rdmName") String rdmname,
                                                              @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {
        List<RdmAllAnalysisTreatyStructure> rdmAllAnalysisTreatyStructure = rmsService.getRdmAllAnalysisTreatyStructure(rdmId, rdmname, analysisIdList);
        return ResponseEntity.ok(rdmAllAnalysisTreatyStructure);
    }

    @GetMapping("RdmAllAnalysisMultiRegionPerils")
    public ResponseEntity<?> getRdmAllAnalysisMultiRegionPerils(@RequestParam(value = "rdmId") Long rdmId,
                                                                @RequestParam(value = "rdmName") String rdmName,
                                                                @RequestParam(value = "analysisIdList", required = false) List<Long> analysisIdList) {

        List<RdmAllAnalysisMultiRegionPerils> rdmAllAnalysisMultiRegionPerils = rmsService.getRdmAllAnalysisMultiRegionPerils(rdmId, rdmName, analysisIdList);

        return ResponseEntity.ok(rdmAllAnalysisMultiRegionPerils);
    }

    @GetMapping("RmsExchangeRates")
    public ResponseEntity<?> getRmsExchangeRates(@RequestParam(value = "ccy") List<String> ccy) {
        List<RmsExchangeRate> rmsExchangeRates = rmsService.getRmsExchangeRates(ccy);
        return ResponseEntity.ok(rmsExchangeRates);
    }

    @GetMapping("CChBaseCcy")
    public ResponseEntity<?> getCChBaseCcy() {
        List<CChkBaseCcy> cChBaseCcy = rmsService.getCChBaseCcy();
        return ResponseEntity.ok(cChBaseCcy);
    }

    @GetMapping("CChkBaseCcyFxRate")
    public ResponseEntity<?> getCChkBaseCcyFxRate() {
        List<CChkBaseCcyFxRate> cChkBaseCcyFxRate = rmsService.getCChkBaseCcyFxRate();
        return ResponseEntity.ok(cChkBaseCcyFxRate);
    }

    @GetMapping("GetAnalysisModellingOptionSettings")
    public String getAnalysisModellingOptionSettings(@RequestParam(value = "rdmId") Long rdmId,
                                                     @RequestParam(value = "rdmName") String rdmName,
                                                     @RequestParam(value = "analysisId") Long analysisId) {
        return rmsService.getAnalysisModellingOptionSettings(null, rdmId, rdmName, analysisId);
    }

    @PostMapping("saveSourceResults")
    public ResponseEntity<?> saveSourceResults(@RequestBody List<SourceResultDto> sourceResultDtoList) {
        try {
            return new ResponseEntity<>(rmsService.saveSourceResults(sourceResultDtoList), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();

            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
