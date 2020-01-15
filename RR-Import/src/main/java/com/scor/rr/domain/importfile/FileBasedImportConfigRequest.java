package com.scor.rr.domain.importfile;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class FileBasedImportConfigRequest {
    private String projectId;
    private List<String> selectedFileSourcePath;
    private boolean isImportLocked;

    public FileBasedImportConfigRequest() {
    }

    public FileBasedImportConfigRequest(String projectId, List<String> selectedFileSourcePath, boolean isImportLocked) {
        this.projectId = projectId;
        this.selectedFileSourcePath = selectedFileSourcePath;
        this.isImportLocked = isImportLocked;
    }

}
