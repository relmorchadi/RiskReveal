package com.scor.rr.importBatch.processing.exposure;

import com.scor.rr.domain.entities.ihub.RRPortfolio;
import com.scor.rr.domain.entities.ihub.RRPortfolioStorage;
import com.scor.rr.domain.entities.ihub.RmsPortfolioAnalysisRegion;
import com.scor.rr.domain.entities.meta.exposure.ExposureSummaryExtractFile;
import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import com.scor.rr.domain.entities.workspace.Portfolio;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.ExposureSummaryExtractType;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import com.scor.rr.importBatch.processing.ylt.meta.XLTOT;
import com.scor.rr.importBatch.processing.ylt.meta.XLTSubType;
import com.scor.rr.repository.ihub.RRPortfolioStorageRepository;
import com.scor.rr.repository.omega.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.sql.Date;
import java.util.List;


/**
 * Created by u004602 on 28/11/2016.
 */
public class ExposureSummaryWriter extends BaseFileWriter {
    private static final Logger log = LoggerFactory.getLogger(ExposureSummaryWriter.class);

    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    RRPortfolioStorageRepository rrPortfolioStorageRepository;

    public ExposureSummaryWriter() {
    }


    public ExposureSummaryWriter(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    public File makeDetailExposureFile(String path, String edmName, String portfolioId) {
        File f = null;
        try {
            Date now = new Date((new java.util.Date()).getTime());
            String regionPeril = "YYYY";
            String fp = "GU";
            XLTOT currencySource = XLTOT.ORIGINAL;
            String currency = "YYY";
            f = makeFullFile(path, makeExposureFileName(XLTSubType.DES, now, regionPeril, fp, currency, currencySource, edmName, portfolioId, null, getFileExtension()));
        } catch (Throwable th) {
            log.error("{}", th);
        } finally {
            return f;
        }
    }

    public File makeLocLevelExposureFile(String edmName, Portfolio portfolio, String extractType) {
        String path = getPrefixDirectory() + "/" + "Exposures";
        File f = null;
        try {
            Date now = new Date((new java.util.Date()).getTime());
            String regionPeril = "YYYY";
            String fp = "GU";
            XLTOT currencySource = XLTOT.ORIGINAL;
            String currency = exposureCurrencyImportData(portfolio.getRmsPortfolio().getAnalysisRegions());
            f = makeFullFile(path, makeExposureFileName(XLTSubType.LOC, now, regionPeril, fp, currency, currencySource, edmName, portfolio.getRmsPortfolio().getPortfolioId(), extractType, getFileExtension()));
        } catch (Throwable th) {

        } finally {
            return f;
        }
    }

    //duplicated from SelectDataSourceBean
    private String exposureCurrencyImportData(List<RmsPortfolioAnalysisRegion> rpar) {

        String currencyCode = null;
        String exposureCurrency = null;

        for (RmsPortfolioAnalysisRegion rmsPortfolioAnalysisRegion : rpar) {
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

    public File makeDetailExposureFile(String edmName, String portfolioId) {
        String path = getPrefixDirectory() + "/" + "Exposures";
        return makeDetailExposureFile(path, edmName, portfolioId);
    }

    public void writeExposureSummaryHeader(Project project, RmsModelDatasource edm, Portfolio portfolio, RRPortfolio rrPortfolio, ExposureSummaryExtractType extractType, List<ExposureSummaryExtractFile> extractFiles) {
        log.debug("Starting writeExposureSummaryHeader");

        // TODO build RR : RRPortfolioStorage, ExposureSummary, TTRMSExposureSummary, TTGlobalView

        for (ExposureSummaryExtractFile esef : extractFiles) {
            RRPortfolioStorage rrPortfolioStorage = new RRPortfolioStorage();
            //mongoDBSequence.nextSequenceId(rrPortfolioStorage);
            rrPortfolioStorage.setProject(project);
            rrPortfolioStorage.setRrPortfolio(rrPortfolio);
            rrPortfolioStorage.setDataType(extractType);
            rrPortfolioStorage.setDataSubType(esef.getExtractFileType());
            rrPortfolioStorage.setOriginalTarget(null); // TODO
            //in case of Detailed Exposure: query by edm (for ALL selected portfolios) -> don't have portfolio id/name
            if (portfolio != null && portfolio.getRmsPortfolio() != null) {
                rrPortfolioStorage.setCurrency(currencyRepository.findByCode(exposureCurrencyImportData(portfolio.getRmsPortfolio().getAnalysisRegions())));
            }
//            rrPortfolioStorage.setExchangeRate(rrPortfolio.getExchangeRate());
//            rrPortfolioStorage.setProportion(rrPortfolio.getProportion());
//            rrPortfolioStorage.setUnitMultiplier(rrPortfolio.getUnitMultiplier());
            rrPortfolioStorage.setFileName(esef.getExtractFile().getFileName());
            rrPortfolioStorage.setFilePath(esef.getExtractFile().getPath());
//            rrPortfolioStorage.setOriginalExposureHeader(null);
            rrPortfolioStorageRepository.save(rrPortfolioStorage);
        }
    }
}
