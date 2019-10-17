package com.scor.rr.service.params;

import com.scor.rr.utils.ALMFUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * ExportPLTParam
 *
 * @author HADDINI Zakariyae
 */
public class ExportPLTParam {

    private static final Logger logger = LoggerFactory.getLogger(ExportPLTParam.class);

    private Integer success;
    private String[] pltIds;
    private File targetDir;
    private String threshold;
    private String ccy;
    private Double thresh;
    private List<String> debugs;
    private List<String> warnings;

    public ExportPLTParam() {

    }

    public ExportPLTParam(Integer success, String[] pltIds, File targetDir, String threshold, String ccy, Double thresh,
                          List<String> debugs, List<String> warnings) {
        super();
        this.success = success;
        this.pltIds = pltIds;
        this.targetDir = targetDir;
        this.threshold = threshold;
        this.ccy = ccy;
        this.thresh = thresh;
        this.debugs = debugs;
        this.warnings = warnings;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String[] getPltIds() {
        return pltIds;
    }

    public File getTargetDir() {
        return targetDir;
    }

    public String getThreshold() {
        return threshold;
    }

    public String getCcy() {
        return ccy;
    }

    public Double getThresh() {
        return thresh;
    }

    public List<String> getDebugs() {
        return debugs;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void checkAndPrepareParamsForGeneratingPLTs() throws Exception {
        if (!targetDir.exists())
            targetDir.mkdirs();

        if (ALMFUtils.isNotNull(threshold))
            threshold = "0";

        if (!ALMFUtils.isNotNull(ccy))
            ccy = "EUR";

        try {
            thresh = Double.valueOf(threshold);
        } catch (Exception e) {
            logger.error("Error during converting value {} - use thresh = 0.0", threshold);
        }
    }

}
