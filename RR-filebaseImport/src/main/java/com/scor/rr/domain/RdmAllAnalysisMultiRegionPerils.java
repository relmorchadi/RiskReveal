package com.scor.rr.domain;

import lombok.Data;

@Data
public class RdmAllAnalysisMultiRegionPerils {

    private Long rdmId;
    private String rdmName;
    private Long analysisId;
    private String ssRegion;
    private String ssPeril;
    private String ssRegionPeril;
    private String profileKey;
    private Long evCount;

    public Long getRdmId() {
        return rdmId;
    }

    public void setRdmId(Long rdmId) {
        this.rdmId = rdmId;
    }

    public String getRdmName() {
        return rdmName;
    }

    public void setRdmName(String rdmName) {
        this.rdmName = rdmName;
    }

    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    public String getSsRegion() {
        return ssRegion;
    }

    public void setSsRegion(String ssRegion) {
        this.ssRegion = ssRegion;
    }

    public String getSsPeril() {
        return ssPeril;
    }

    public void setSsPeril(String ssPeril) {
        this.ssPeril = ssPeril;
    }

    public String getSsRegionPeril() {
        return ssRegionPeril;
    }

    public void setSsRegionPeril(String ssRegionPeril) {
        this.ssRegionPeril = ssRegionPeril;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public Long getEvCount() {
        return evCount;
    }

    public void setEvCount(Long evCount) {
        this.evCount = evCount;
    }
}


