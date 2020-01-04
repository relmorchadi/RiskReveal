package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.ModelPortfolioEntity;
import com.scor.rr.domain.ModelPortfolioStorageEntity;
import com.scor.rr.domain.enums.ExposureSummaryExtractType;
import com.scor.rr.domain.enums.XLTOT;
import com.scor.rr.domain.enums.XLTSubType;
import com.scor.rr.domain.model.ExposureSummaryExtractFile;
import com.scor.rr.domain.riskLink.RLPortfolio;
import com.scor.rr.domain.riskLink.RLPortfolioAnalysisRegion;
import com.scor.rr.repository.ModelPortfolioStorageRepository;
import com.scor.rr.util.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
@StepScope
@Slf4j
public class ExposureWriter extends AbstractWriter {


    @Autowired
    private ModelPortfolioStorageRepository modelPortfolioStorageRepository;

    public File makeDetailExposureFile(String edmName, Long portfolioId, Integer division) {
        String path = PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)) + "/" + "Exposures";
        return makeDetailExposureFile(path, edmName, portfolioId, division);
    }

    public File makeDetailExposureFile(String path, String edmName, Long portfolioId, Integer division) {

        try {
            Date now = new Date();
            String regionPeril = "YYYY";
            String fp = "GU";
            XLTOT currencySource = XLTOT.ORIGINAL;
            String currency = "YYY";
            return makeFullFile(path, makeExposureFileName(XLTSubType.DES, now, regionPeril, fp, currency, currencySource, edmName, portfolioId, null, division, ".bin"));
        } catch (Throwable th) {
            log.error("{}", th);
            return null;
        }
    }

    public void writeExposureSummaryHeader(Long edmId, String edmName, RLPortfolio rlPortfolio, ModelPortfolioEntity modelPortfolio, ExposureSummaryExtractType extractType, List<ExposureSummaryExtractFile> extractFiles) {
        log.debug("Starting writeExposureSummaryHeader");

        for (ExposureSummaryExtractFile exposureSummaryExtractFile : extractFiles) {
            ModelPortfolioStorageEntity modelPortfolioStorage = new ModelPortfolioStorageEntity();
            modelPortfolioStorage.setProjectId(modelPortfolio.getProjectId());
            modelPortfolioStorage.setModelPortfolioId(modelPortfolio.getModelPortfolioId());
            modelPortfolioStorage.setDataType(extractType.toString());
            modelPortfolioStorage.setDataSubType(exposureSummaryExtractFile.getExtractFileType());
            //in case of Detailed Exposure: query by edm (for ALL selected portfolios) -> don't have portfolio id/name

            if (rlPortfolio != null) {
                modelPortfolioStorage.setCurrency(exposureCurrencyImportData(rlPortfolio.getRlPortfolioAnalysisRegions()));
            }

            modelPortfolioStorage.setFileName(exposureSummaryExtractFile.getExtractFile().getFileName());
            modelPortfolioStorage.setFilePath(exposureSummaryExtractFile.getExtractFile().getPath());
            modelPortfolioStorageRepository.save(modelPortfolioStorage);
        }
    }

    public File makeLocLevelExposureFile(String edmName, Long rlPortfolioId, String extractType, List<RLPortfolioAnalysisRegion> rlPortfolioAnalysisRegions, Integer division) {
        String path = PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)) + "/" + "Exposures";
        try {
            Date now = new Date();
            String regionPeril = "YYYY";
            String fp = "GU";
            XLTOT currencySource = XLTOT.ORIGINAL;
            String currency = exposureCurrencyImportData(rlPortfolioAnalysisRegions);
            return makeFullFile(path, makeExposureFileName(XLTSubType.LOC, now, regionPeril, fp, currency, currencySource, edmName, rlPortfolioId, extractType, division,".csv"));
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
