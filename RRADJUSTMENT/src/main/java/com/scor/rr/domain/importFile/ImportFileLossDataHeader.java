package com.scor.rr.domain.importFile;
import java.util.*;

public class ImportFileLossDataHeader {
    private Date lastScanDate;
    private Map<String, String> metadata; // map id cua
    private List<String> scanErrors;

    public Date getLastScanDate() {
        return lastScanDate;
    }

    public void setLastScanDate(Date lastScanDate) {
        this.lastScanDate = lastScanDate;
    }

    public Map<String, String> getMetadata() {
        if (metadata == null) {
            metadata = new HashMap<>();
        }
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public List<String> getScanErrors() {
        if (scanErrors == null) {
            scanErrors = new ArrayList<>();
        }
        return scanErrors;
    }

    public void setScanErrors(List<String> scanErrors) {
        this.scanErrors = scanErrors;
    }
}
