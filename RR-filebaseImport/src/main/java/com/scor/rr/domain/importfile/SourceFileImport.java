package com.scor.rr.domain.importfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U005342 on 26/07/2018.
 */
public class SourceFileImport {
    private String projectId;
    private String filePath;
    private String fileName;
    private ImportFileLossDataHeader importFileHeader;
    private boolean validatedHeader = true;
    private boolean validatedData = true;
    private List<String> errorMessages;
    //these value will be filled while validating file
    private int modelVersionYear;
    private String targetRapCode;
    private List<String> regionPerilCodes;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getModelVersionYear() {
        return modelVersionYear;
    }

    public void setModelVersionYear(int modelVersionYear) {
        this.modelVersionYear = modelVersionYear;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ImportFileLossDataHeader getImportFileHeader() {
        return importFileHeader;
    }

    public void setImportFileHeader(ImportFileLossDataHeader importFileHeader) {
        this.importFileHeader = importFileHeader;
    }

    public boolean isValidatedHeader() {
        return validatedHeader;
    }

    public void setValidatedHeader(boolean validatedHeader) {
        this.validatedHeader = validatedHeader;
    }

    public boolean isValidatedData() {
        return validatedData;
    }

    public void setValidatedData(boolean validatedData) {
        this.validatedData = validatedData;
    }

    public List<String> getErrorMessages() {
        if (errorMessages == null) {
            errorMessages = new ArrayList<>();
        }
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getTargetRapCode() {
        return targetRapCode;
    }

    public void setTargetRapCode(String targetRapCode) {
        this.targetRapCode = targetRapCode;
    }

    public List<String> getRegionPerilCodes() {
        if (regionPerilCodes == null) {
            regionPerilCodes = new ArrayList<>();
        }
        return regionPerilCodes;
    }

    public void setRegionPerilCodes(List<String> regionPerilCodes) {
        this.regionPerilCodes = regionPerilCodes;
    }
}

