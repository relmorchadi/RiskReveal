package com.scor.rr.importBatch.processing.utils;

import com.scor.rr.domain.enums.PLTSimulationPeriod;
import com.scor.rr.importBatch.processing.ylt.meta.*;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by u004602 on 30/08/2017.
 */
public class TTPathUtils {
    public static String getPrefixDirectory(String clientName, String clientId, String contractId, String uwYear, String projectId) {
        List<String> items = new ArrayList<>();
        items.add("Treaty");
        items.add("Cedant");
        if (clientName != null && clientId != null)
            items.add(new StringBuilder(clientName.replaceAll("[\\\\/:*?\"<>|]", "_")).append("-").append(clientId).toString());
        if (contractId != null)
            items.add(contractId);
        if (uwYear != null)
            items.add(uwYear);
        if (projectId != null)
            items.add(projectId);
        return StringUtils.join(items, "/");
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
            Integer targetRapId,
            Integer simulationPeriod,
            PLTPublishStatus pltPublishStatus,
            Integer threadNum, // 0 for pure PLT
            String uniqueId,
            Long importSequence,
            String edmName,
            String portfolioId,
            String fileNature,
            String fileExtension) {
        String simBasis = PLTSimulationPeriod.SIM800K.toString().equals(simulationPeriod) ? "M".concat("-").concat("800") : "N".concat("-").concat("100");
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
}
