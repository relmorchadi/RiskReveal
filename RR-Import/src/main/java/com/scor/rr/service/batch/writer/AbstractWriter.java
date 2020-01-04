package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.enums.*;
import com.scor.rr.util.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component
@StepScope
@Slf4j
public abstract class AbstractWriter {

    @Value("#{jobParameters['projectId']}")
    protected String projectId;

    @Value("#{jobParameters['clientId']}")
    protected String clientId;

    @Value("#{jobParameters['clientName']}")
    protected String clientName;

    @Value("#{jobParameters['uwYear']}")
    protected String uwYear;

    @Value("#{jobParameters['contractId']}")
    protected String contractId;

    @Value("#{jobParameters['reinsuranceType']}")
    protected String reinsuranceType;

    @Value("#{jobParameters['prefix']}")
    protected String prefix;

    @Value("#{jobParameters['division']}")
    protected String division;

    @Value("#{jobParameters['sourceVendor']}")
    protected String sourceVendor;

    @Value("#{jobParameters['modelSystemVersion']}")
    protected String modelSystemVersion;

    @Value("#{jobParameters['periodBasis']}")
    protected String periodBasis;

    @Value("#{jobParameters['importSequence']}")
    protected Long importSequence;

    @Value("#{jobParameters['carId']}")
    protected String carId;

    private Path ihubPath;

    @Value("${ihub.treaty.out.path}")
    private void setIhubPath(String path) {
        this.ihubPath = Paths.get(path);
    }

    protected synchronized String makeELTFileName(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long uniqueId, Integer myDivision, String fileExtension) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                myDivision != null ? String.valueOf(myDivision) : division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.DAT,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension
        );
    }

    protected synchronized String makeAPSFileName(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long uniqueId, Integer myDivision, String fileExtension) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                myDivision != null ? String.valueOf(myDivision) : division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.APS,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension
        );
    }

    protected synchronized String makePLTFileName(
            Date date, String regionPeril, String fp, String currency, XLTOT currencySource, Long targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus,
            Integer threadNum, // 0 for pure PLT
            Long uniqueId, Integer myDivision, String fileExtension) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                myDivision != null ? String.valueOf(myDivision) : division,
                uwYear,
                XLTAssetType.PLT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.INTERNAL,
                XLTSubType.DAT,
                currencySource,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum, // 0 for pure PLT
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension
        );
    }

    protected synchronized String makeELTEPCurveFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long uniqueId, Integer myDivision, String fileExtension) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                myDivision != null ? String.valueOf(myDivision) : division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.EPC,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension
        );
    }

    protected synchronized String makeELTSummaryStatFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long uniqueId, Integer myDivision, String fileExtension) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                myDivision != null ? String.valueOf(myDivision) : division,
                uwYear,
                XLTAssetType.ELT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.MODEL,
                XLTSubType.EPS,
                xltot,
                null,
                null,
                null,
                null,
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension
        );
    }

    protected synchronized String makeExposureFileName(XLTSubType subType, Date date, String regionPeril, String fp, String currency, XLTOT currencySource, String edmName, Long portfolioId, String fileNature, Integer myDivision, String fileExtension) {
        return PathUtils.makeTTFileName(reinsuranceType,
                prefix,
                clientName,
                contractId,
                myDivision != null ? String.valueOf(myDivision) : division,
                uwYear,
                XLTAssetType.EXP,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                null,
                periodBasis,
                XLTOrigin.INTERNAL,
                subType,
                currencySource,
                null,
                null,
                null,
                null,
                null,
                importSequence,
                edmName,
                portfolioId,
                fileNature,
                fileExtension);
    }

    protected File makeFullFile(String prefixDirectory, String filename) {
        final Path fullPath = ihubPath.resolve(prefixDirectory);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths " + fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }

    protected synchronized String makePLTEPCurveFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus, Integer threadNum, // 0 for pure PLT
            Long uniqueId, Integer myDivision, String fileExtension) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                myDivision != null ? String.valueOf(myDivision) : division,
                uwYear,
                XLTAssetType.PLT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.INTERNAL,
                XLTSubType.EPC,
                xltot,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum,
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension
        );
    }

    protected synchronized String makePLTSummaryStatFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus, Integer threadNum, // 0 for pure PLT
            Long uniqueId, Integer myDivision, String fileExtension) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                myDivision != null ? String.valueOf(myDivision) : division,
                uwYear,
                XLTAssetType.PLT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.INTERNAL,
                XLTSubType.EPS,
                xltot,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum,
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension
        );
    }

    protected synchronized String makeFacFilename(String jobType, Date runDate, XLTSubType xltSubType, String suffix, String fileExtension, Integer myDivision) {
        return PathUtils.makeTTFileName(
                reinsuranceType,
                jobType,
                clientName,
                contractId,
                myDivision != null ? String.valueOf(myDivision) : division,
                uwYear,
                XLTAssetType.EXP,
                runDate,
                sourceVendor,
                modelSystemVersion,
                carId,
                periodBasis,
                XLTOrigin.INTERNAL,
                xltSubType,
                suffix,
                fileExtension
        );
    }
}
