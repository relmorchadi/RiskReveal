package com.scor.rr.util;

import com.scor.rr.domain.ModelAnalysisEntity;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.enums.*;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PathUtils {
    public static String makeEpCurveFileName(
            String financialPersp,
            Date creationDate,
            String fileExtension) {
        // @TODO Review the required params for this
        return "EP_CURVE" + String.valueOf(creationDate.getTime()).concat(financialPersp).concat(".").concat(fileExtension);
    }

    public static String makeEpSummaryStatFileName(
            String financialPersp,
            Date creationDate,
            String fileExtension) {
        // @TODO Review the required params for this
        return "SUMMARY_STAT" + String.valueOf(creationDate.getTime()).concat(financialPersp).concat(".").concat(fileExtension);
    }

    public static File makeFullFile(String prefixDirectory, String filename, Path ihubPath) {
        final Path fullPath = ihubPath.resolve(prefixDirectory);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            throw new RuntimeException("error creating paths " + fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }

    public static String getPrefixDirectory(String clientName, Long clientId, String contractId, Integer uwYear, Long projectId) {
        List<String> items = new ArrayList<>();
        items.add("Treaty");
        items.add("Cedant");
        if (clientName != null && clientId != null)
            items.add(new StringBuilder(clientName.replaceAll("[\\\\/:*?\"<>|]", "_")).append("-").append(clientId).toString());
        if (contractId != null)
            items.add(contractId);
        if (uwYear != null)
            items.add(String.valueOf(uwYear));
        if (projectId != null)
            items.add(String.valueOf(projectId));
        return org.apache.commons.lang.StringUtils.join(items, "/");
    }

    public static String getPrefixDirectoryFac(String clientName, Long clientId, String contractId, Integer uwYear, Integer division, String carId, Long importSequence) {
        List<String> items = new ArrayList<>();
        items.add("Facultative");
        items.add("Contracts");
//        if (clientName != null && clientId != null)
//            items.add(new StringBuilder(clientName.replaceAll("[\\\\/:*?\"<>|]", "_")).append("-").append(clientId).toString());
        if (contractId != null)
            items.add(contractId);
        if (uwYear != null)
            items.add(String.valueOf(uwYear));
        if (carId != null)
            items.add(carId);
        if (division != null)
            items.add(String.valueOf(division));
        if (importSequence != null)
            items.add("import " + importSequence);

        return org.apache.commons.lang.StringUtils.join(items, "/");
    }

    public static String makeTTFileName(
            String reinsuranceType,
            String prefix,
            String clientName,
            String contractId,
            String division,
            String uwYear,
            XLTAssetType XLTAssetType,
            Date date,
            String sourceVendor,
            String modelSystemVersion,
            String regionPeril,
            String fp,
            String currency,
            String projectId,
            String periodBasis,
            XLTOrigin origin,
            XLTSubType subType,
            XLTOT currencySource,
            Long targetRapId,
            Integer simulationPeriod,
            PLTPublishStatus pltPublishStatus,
            Integer threadNum, // 0 for pure PLT
            Long uniqueId,
            Long importSequence,
            String edmName,
            Long portfolioId,
            String fileNature,
            String fileExtension) {
        String simBasis = PLTSimulationPeriod.SIM800K.getCode().equals(simulationPeriod) ? "M".concat("-").concat("800") : "N".concat("-").concat("100");
        List<String> items = new ArrayList<>();
        if (reinsuranceType != null) {
            items.add(reinsuranceType);
        }
        if (prefix != null) {
            items.add(prefix);
        }
        if (clientName != null) {
            String name = clientName.trim().replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
            items.add(String.format("%1.20s", name));
        }
        if (contractId != null) {
            items.add(contractId);
        }
        if (division != null) {
            items.add(division);
        }
        if (uwYear != null) {
            items.add(uwYear);
        }
        if (XLTAssetType != null) {
            items.add(XLTAssetType.toString());
        }
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String format = formatter.format(date);
            items.add(format);
        }
        if (sourceVendor != null) {
            items.add(sourceVendor);
        }
        if (modelSystemVersion != null) {
            items.add(modelSystemVersion);
        }
        if (regionPeril != null) {
            items.add(regionPeril);
        }
        if (fp != null) {
            items.add(fp);
        }
        if (currency != null) {
            items.add(currency);
        }
        if (projectId != null) {
            items.add(projectId);
        }
        if (periodBasis != null) {
            items.add(periodBasis);
        }
        if (origin != null) {
            items.add(origin.toString());
        }
        if (subType != null) {
            items.add(subType.toString());
        }
        if (currencySource != null) {
            items.add(currencySource.getValue());
        }
        if (targetRapId != null) {
            items.add("RAP-" + targetRapId);
        }
        if (simulationPeriod != null) {
            items.add(simBasis);
        }
        if (pltPublishStatus != null && threadNum != null) {
            items.add(pltPublishStatus.getValue() + "-T-" + threadNum);
        }
        if (uniqueId != null) {
            items.add("ID-" + uniqueId);
        }
        if (importSequence != null) {
            items.add("Job-" + importSequence);
        } else {
            items.add("Job-X");
        }
        if (edmName != null) {
            items.add(edmName);
        }
        if (portfolioId != null) {
            items.add("P" + portfolioId);
        }
        if (fileNature != null) {
            items.add(fileNature);
        }

        String filename = StringUtils.join(items, "_");
        StringBuilder builder = new StringBuilder(filename).append(fileExtension);
//        String finalName = fileName.trim().replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
//        String finalName = fileName.trim().replaceAll(" ", "-");//.replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
        return builder.toString();
    }

    public static String makePLTFileName(WorkspaceEntity workspaceEntity,
                                         ModelAnalysisEntity modelAnalysis,
                                         PltHeaderEntity plt,
                                         Integer threadId,
                                         Integer nodeId,
                                         String fileExtension) {
        return PathUtils.makePLTFileName(workspaceEntity != null && workspaceEntity.getWorkspaceMarketChannel() != null && 2 == workspaceEntity.getWorkspaceMarketChannel() ? "F" : "T",
                null,
                workspaceEntity != null ? workspaceEntity.getClientName() : null,
                workspaceEntity != null ? workspaceEntity.getWorkspaceContextCode() : null,
                null,
                workspaceEntity.getWorkspaceUwYear().toString(),
                XLTAssetType.PLT,
                new Date(),
                modelAnalysis.getSourceModellingVendor(),
                modelAnalysis.getSourceModellingSystemVersion(),
                modelAnalysis.getRegionPeril(),
                "UF",
                plt.getCurrencyCode(),
                plt.getProjectId(),
                "FT",
                XLTOrigin.INTERNAL,
                XLTSubType.DAT,
                XLTOT.TARGET,
                plt.getTargetRAPId(),
                plt.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                threadId,
                plt.getPltHeaderId(),
                plt.getImportSequence(),
                nodeId,
                null,
                fileExtension);
    }

    public static String makePLTFileName(
            String reinsuranceType,
            String prefix,
            String clientName,
            String contractId,
            String division,
            String uwYear,
            XLTAssetType XLTAssetType,
            Date date,
            String sourceVendor,
            String modelSystemVersion,
            String regionPeril,
            String fp,
            String currency,
            Long projectId,
            String periodBasis,
            XLTOrigin origin,
            XLTSubType subType,
            XLTOT currencySource,
            Long targetRapId,
            Integer simulationPeriod,
            PLTPublishStatus pltPublishStatus,
            Integer threadNum, // 0 for pure PLT
            Long uniqueId,
            Integer importSequence,
            Integer nodeId,
            String fileNature,
            String fileExtension) {
        String simBasis = PLTSimulationPeriod.SIM800K.getCode().equals(simulationPeriod) ? "M".concat("-").concat("800") : "N".concat("-").concat("100");
        List<String> items = new ArrayList<>();
        if (reinsuranceType != null) {
            items.add(reinsuranceType);
        }
        if (prefix != null) {
            items.add(prefix);
        }
        if (clientName != null) {
            String name = clientName.trim().replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
            items.add(String.format("%1.20s", name));
        }
        if (contractId != null) {
            items.add(contractId);
        }
        if (division != null) {
            items.add(division);
        }
        if (uwYear != null) {
            items.add(uwYear);
        }
        if (XLTAssetType != null) {
            items.add(XLTAssetType.toString());
        }
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String format = formatter.format(date);
            items.add(format);
        }
        if (sourceVendor != null) {
            items.add(sourceVendor);
        }
        if (modelSystemVersion != null) {
            items.add(modelSystemVersion);
        }
        if (regionPeril != null) {
            items.add(regionPeril);
        }
        if (fp != null) {
            items.add(fp);
        }
        if (currency != null) {
            items.add(currency);
        }
        if (projectId != null) {
            items.add(projectId.toString());
        }
        if (periodBasis != null) {
            items.add(periodBasis);
        }
        if (origin != null) {
            items.add(origin.toString());
        }
        if (subType != null) {
            items.add(subType.toString());
        }
        if (currencySource != null) {
            items.add(currencySource.getValue());
        }
        if (targetRapId != null) {
            items.add("RAP-" + targetRapId);
        }
        if (simulationPeriod != null) {
            items.add(simBasis);
        }
        if (pltPublishStatus != null && threadNum != null) {
            items.add(pltPublishStatus.getValue() + "-T-" + threadNum);
        }
        if (uniqueId != null) {
            items.add("ID-" + uniqueId);
        }
        if (importSequence != null) {
            items.add("Job-" + importSequence);
        } else {
            items.add("Job-X");
        }
        if (nodeId != null) {
            items.add("Node_" + nodeId.toString());
        }

        if (fileNature != null) {
            items.add(fileNature);
        }

        String filename = StringUtils.join(items, "_");
        StringBuilder builder = new StringBuilder(filename).append("." + fileExtension);
//        String finalName = fileName.trim().replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
//        String finalName = fileName.trim().replaceAll(" ", "-");//.replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
        return builder.toString();
    }

    public static String makeTTFileName(String reinsuranceType, String jobType, String clientName, String contractId, String division, String uwYear, XLTAssetType exp, Date runDate, String sourceVendor, String modelSystemVersion, String carId, String periodBasis, XLTOrigin internal, XLTSubType xltSubType, String suffix, String fileExtension) {
        List<String> items = new ArrayList<>();
        if (reinsuranceType != null) {
            items.add(reinsuranceType);
        }
        if (jobType != null) {
            items.add(jobType);
        }
        if (clientName != null) {
            String name = clientName.trim().replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
            items.add(String.format("%1.20s", name));
        }
        if (contractId != null) {
            items.add(contractId);
        }
        if (division != null) {
            items.add(division);
        }
        if (uwYear != null) {
            items.add(uwYear);
        }
        if (exp != null) {
            items.add(exp.toString());
        }
        if (runDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String format = formatter.format(runDate);
            items.add(format);
        }
        if (sourceVendor != null) {
            items.add(sourceVendor);
        }
        if (modelSystemVersion != null) {
            items.add(modelSystemVersion);
        }
        if (carId != null) {
            items.add(carId);
        }
        if (periodBasis != null) {
            items.add(periodBasis);
        }
        if (internal != null) {
            items.add(internal.toString());
        }
        if (xltSubType != null) {
            items.add(xltSubType.toString());
        }
        if (suffix != null) {
            items.add(suffix);
        }

        String filename = StringUtils.join(items, "_");
        StringBuilder builder = new StringBuilder(filename).append(fileExtension);
//        String finalName = fileName.trim().replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
//        String finalName = fileName.trim().replaceAll(" ", "-");//.replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
        return builder.toString();
    }
}
