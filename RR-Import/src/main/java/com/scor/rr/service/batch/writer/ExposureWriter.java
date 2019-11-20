package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.enums.XLTOT;
import com.scor.rr.domain.enums.XLTSubType;
import com.scor.rr.domain.riskLink.RLPortfolioAnalysisRegion;
import com.scor.rr.util.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
@StepScope
@Slf4j
public class ExposureWriter extends AbstractWriter {

    public File makeDetailExposureFile(String edmName, Long portfolioId) {
        String path = PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)) + "/" + "Exposures";
        return makeDetailExposureFile(path, edmName, portfolioId);
    }

    public File makeDetailExposureFile(String path, String edmName, Long portfolioId) {

        try {
            Date now = new Date();
            String regionPeril = "YYYY";
            String fp = "GU";
            XLTOT currencySource = XLTOT.ORIGINAL;
            String currency = "YYY";
            return makeFullFile(path, makeExposureFileName(XLTSubType.DES, now, regionPeril, fp, currency, currencySource, edmName, portfolioId, null, ".bin"));
        } catch (Throwable th) {
            log.error("{}", th);
            return null;
        }
    }

//    public void writeExposureSummaryHeader(Project project, RmsModelDatasource edm, Portfolio portfolio, RRPortfolio rrPortfolio, ExposureSummaryExtractType extractType, List<ExposureSummaryExtractFile> extractFiles) {
//        log.debug("Starting writeExposureSummaryHeader");
//
//        // TODO build RR : RRPortfolioStorage, ExposureSummary, TTRMSExposureSummary, TTGlobalView
//
//        for (ExposureSummaryExtractFile esef : extractFiles) {
//            RRPortfolioStorage rrPortfolioStorage = new RRPortfolioStorage();
//            mongoDBSequence.nextSequenceId(rrPortfolioStorage);
//            rrPortfolioStorage.setProjectId(project.getId());
//            rrPortfolioStorage.setRrPortfolioId(rrPortfolio.getId());
//            rrPortfolioStorage.setDataType(extractType.toString());
//            rrPortfolioStorage.setDataSubType(esef.getExtractFileType());
//            rrPortfolioStorage.setOriginalTarget(null); // TODO
//            //in case of Detailed Exposure: query by edm (for ALL selected portfolios) -> don't have portfolio id/name
//            if (portfolio != null && portfolio.getRmsPortfolio() != null) {
//                rrPortfolioStorage.setCurrency(exposureCurrencyImportData(portfolio.getRmsPortfolio().getAnalysisRegions()));
//            }
////            rrPortfolioStorage.setExchangeRate(rrPortfolio.getExchangeRate());
////            rrPortfolioStorage.setProportion(rrPortfolio.getProportion());
////            rrPortfolioStorage.setUnitMultiplier(rrPortfolio.getUnitMultiplier());
//            rrPortfolioStorage.setFileName(esef.getExtractFile().getFileName());
//            rrPortfolioStorage.setFilePath(esef.getExtractFile().getPath());
////            rrPortfolioStorage.setOriginalExposureHeader(null);
//            rrPortfolioStorageRepository.save(rrPortfolioStorage);
//        }
//    }

    public File makeLocLevelExposureFile(String edmName, Long rlPortfolioId, String extractType, List<RLPortfolioAnalysisRegion> rlPortfolioAnalysisRegions) {
        String path = PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)) + "/" + "Exposures";
        try {
            Date now = new Date();
            String regionPeril = "YYYY";
            String fp = "GU";
            XLTOT currencySource = XLTOT.ORIGINAL;
            String currency = exposureCurrencyImportData(rlPortfolioAnalysisRegions);
            return makeFullFile(path, makeExposureFileName(XLTSubType.LOC, now, regionPeril, fp, currency, currencySource, edmName, rlPortfolioId, extractType, ".csv"));
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private String exposureCurrencyImportData(List<RLPortfolioAnalysisRegion> rpar) {

        String currencyCode = null;
        String exposureCurrency = null;

        for (RLPortfolioAnalysisRegion rmsPortfolioAnalysisRegion : rpar) {
            //log.info("{}", rmsPortfolioAnalysisRegion.getExposureCurrency());
            if (currencyCode == null) {
                currencyCode = rmsPortfolioAnalysisRegion.getExposureCurrency();
            }

            if (!rmsPortfolioAnalysisRegion.getExposureCurrency().equals(currencyCode)) {
                //exposureCurrency = SelectDatasourcesUtils.ExposureCurrency.MULTIPLE;
                exposureCurrency = "MULTIPLE";
                break;
            }
        }

        if (exposureCurrency == null) {
            //exposureCurrency = SelectDatasourcesUtils.ExposureCurrency.SINGLE;
            exposureCurrency = currencyCode;
        }

        return exposureCurrency;
    }
}
