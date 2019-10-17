package com.scor.rr.importBatch.processing.statistics;

import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.domain.entities.references.cat.Division;
import com.scor.rr.importBatch.businessrules.bean.BusinessRulesBean;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import com.scor.rr.importBatch.processing.domain.FWData;
import com.scor.rr.repository.cat.CATAnalysisModelResultsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by U002629 on 11/05/2015.
 */
public class AccLocFilesHandlerImpl extends BaseFileWriter implements AccLocFilesHandler {
    private static final Logger log = LoggerFactory.getLogger(AccLocFilesHandlerImpl.class);
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);
    private static final NumberFormat format;

    static {
        format = NumberFormat.getInstance();
        format.setMinimumIntegerDigits(2);
        format.setGroupingUsed(false);
    }

    private final String ihubFwPath;
    private CATAnalysisModelResultsRepository catAnalysisModelResultsRepository;
    private String filePath;
    private FWData fwData;

    public AccLocFilesHandlerImpl(String filePath, String ihubFwPath) {
        this.filePath = filePath;
        this.ihubFwPath = ihubFwPath;
    }

    public void setCatAnalysisModelResultsRepository(CATAnalysisModelResultsRepository catAnalysisModelResultsRepository) {
        this.catAnalysisModelResultsRepository = catAnalysisModelResultsRepository;
    }

    public void setFwData(FWData fwData) {
        this.fwData = fwData;
    }

    @Override
    protected Map<String, Object> fireRules(String... ruleNames) {
        return fireRules(null, ruleNames);
    }

    @Override
    protected synchronized Map<String, Object> fireRules(Map<String, Object> inputParameters, String... ruleNames) {
        BusinessRulesBean businessBean = new BusinessRulesBean();
        businessBean.setInputData(inputParameters);
        for (String ruleName : ruleNames) {
            businessRulesService.runRuleByName(businessBean, ruleName);
        }
        return businessBean.getOutputData();
    }

    @Override
    public Boolean copyFilesToFW() {
        final CATRequest request = fwData.myRequest();
        String catReqId = request.getCatRequestId();
        Date runDate = new Date();
//        for (CATAnalysisModelResults catAnalysisModelResults : catAnalysisModelResultsRepository.queryForDivisionList(CATAnalysis.buildId(catReqId), PeriodBasisStatus.FULL)) {
//            Integer divId = catAnalysisModelResults.getDivisionNumber();
//            copyFiles(Paths.get(ihubFwPath), runDate, divId, request, true, ".acc", "_FW.loc", ".vls", ".txt");
//            copyFilesToIhub(request, divId.toString(), runDate, catReqId);
//        }
        return true;
    }

    @Override
    public Boolean copyFilesToIhub() {
        return copyFilesToIhub((CATRequest) loadRequest(), division, runDate, catReqId);
    }

    protected Boolean copyFilesToIhub(CATRequest request, String division, Date runDate, String catReqId) {
        Map<String, Object> inputParameters = new HashMap<>();
        inputParameters.put("type", "EXP");
        inputParameters.put("periodBasisStr", "FT");

        inputParameters.put("domain", "FAC");
        inputParameters.put("jobType", "ACCOUNT");
        inputParameters.put("division", division);
        inputParameters.put("periodBasis", "FT");
        inputParameters.put("extractionDate", runDate);
        inputParameters.put("catRequest", request);
        inputParameters.put("carID", catReqId);
//        inputParameters.put("vendor", ModellingVendor.getVendorFromInstance(request.getModellingSystemInstance()));
//        inputParameters.put("system", ModellingSystem.getSystemFromInstance(request.getModellingSystemInstance()));
//        inputParameters.put("version", ModellingSystemVersion.getVersionFromInstance(request.getModellingSystemInstance()));
        inputParameters.put("type", "PLT");
        inputParameters.put("fpToUse", "UP");

        Map<String, Object> pathElements4 = fireRules(inputParameters, "firstDir", "pathElements");
        String firstDir = (String) pathElements4.get("firstDir");
        String[] dirElements = (String[]) pathElements4.get("pathElements");
        Path dirLocl = Paths.get(firstDir, dirElements);
        copyFiles(Paths.get(filePath).resolve(dirLocl), runDate, Integer.parseInt(division), request, false, ".acc", ".loc", ".vls", ".txt");

        return true;
    }

    private void copyFiles(Path targetPath, Date runDate, Integer divId, CATRequest request, boolean copyValues, String accSuffix, String locSuffix, String vlsSuffix, String targetSuffix) {
        try {
            final Path ihubPath = Paths.get(filePath);

            Map<String, Object> inputParameters = new HashMap<>();
            inputParameters.put("type", "EXP");
            inputParameters.put("periodBasisStr", "FT");

            inputParameters.put("domain", "FAC");
            inputParameters.put("jobType", "ACCOUNT");
            inputParameters.put("division", Integer.toString(divId));
            inputParameters.put("periodBasis", "FT");
            inputParameters.put("extractionDate", runDate);
            inputParameters.put("catRequest", request);
            inputParameters.put("carID", request.getCatRequestId());
//            inputParameters.put("vendor", ModellingVendor.getVendorFromInstance(request.getModellingSystemInstance()));
//            inputParameters.put("system", ModellingSystem.getSystemFromInstance(request.getModellingSystemInstance()));
//            inputParameters.put("version", ModellingSystemVersion.getVersionFromInstance(request.getModellingSystemInstance()));

            Map<String, Object> pathElements = fireRules(inputParameters, "baseFileName");
            String baseFileName = ((String) pathElements.get("baseFileName")).trim();//.replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");

            inputParameters.put("fpToUse", "UP");
            Map<String, Object> pathElements3 = fireRules(inputParameters, "fwSuffix");


            final Path tmpPath = ihubPath.resolve("tmp");
            final String baseTmpName = request.getCatRequestId() + "_" + divId.toString();

            String finalNameAcc = doCopy(baseTmpName, baseFileName, pathElements3.get("fwACC").toString(), accSuffix, targetSuffix, tmpPath, targetPath);
            //request.getAccFileNames().put(divId, finalNameAcc);

            String finalNameLoc = doCopy(baseTmpName, baseFileName, pathElements3.get("fwLOC").toString(), locSuffix, targetSuffix, tmpPath, targetPath);
            //request.getLocFileNames().put(divId, finalNameLoc);

            if (copyValues) {
                inputParameters.put("type", "PLT");
                Map<String, Object> pathElements2 = fireRules(inputParameters, "baseFileName");
                String baseFileNameEPC = ((String) pathElements2.get("baseFileName")).trim();//.replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
                String finalNameEpc = doCopy(baseTmpName, baseFileNameEPC, pathElements3.get("fwEPC").toString(), vlsSuffix, targetSuffix, tmpPath, targetPath);
                //request.getVlsFileNames().put(divId, finalNameEpc);
            }

        } catch (IOException e) {
            log.error("FW MOVE FILES ERROR: ", e);
            throw new RuntimeException(e);
        }
    }

    private String doCopy(String baseSourceName, String baseTargetName, String complement, String sourceExtension, String targetExtension, Path sourcePath, Path targetPath) throws IOException {
        String targetFileName = new StringBuilder().append(baseTargetName).append(complement).append(targetExtension).toString();
        String finalTargetFileName = targetFileName.trim();//.replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");
        final Path tmpSourcePath = sourcePath.resolve(baseSourceName + sourceExtension);
        final Path targetFilePath = targetPath.resolve(finalTargetFileName);
        log.info("writing " + finalTargetFileName);
        Files.copy(tmpSourcePath, targetFilePath);

        return finalTargetFileName;
    }

    @Override
    public boolean removeTmpFiles() {
        final Path ihubPath = Paths.get(filePath);
        final CATRequest request = fwData.myRequest();
        final Path tmpPath = ihubPath.resolve("tmp");
        for (Division div : request.getUwAnalysis().getDivisions()) {
            //TODO : check div.id or div.number
            final String baseTmpName = request.getCatRequestId() + "_" + div.getId();
            final Path tmpAccPath = tmpPath.resolve(baseTmpName + ".acc");
            final Path tmpLocPath = tmpPath.resolve(baseTmpName + ".loc");
            final Path tmpFWLocPath = tmpPath.resolve(baseTmpName + "_FW.loc");
            final Path tmpVlsPath = tmpPath.resolve(baseTmpName + ".vls");
            try {
                Files.deleteIfExists(tmpAccPath);
                Files.deleteIfExists(tmpLocPath);
                Files.deleteIfExists(tmpFWLocPath);
                Files.deleteIfExists(tmpVlsPath);
            } catch (IOException e) {
                log.error("REMOVE TMP FILES ERROR: ", e);
                throw new RuntimeException(e);
            }
        }
        return true;
    }

}
