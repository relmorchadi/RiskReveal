package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmAllAnalysisProfileRegions {

    private Long analysisid;
    private String anlysisRegion;
    private String analysisRegionName;
    private String profileRegion;
    private String profileRegionName;
    private String Peril;
    private BigDecimal aal;

    public Long getAnalysisid() {
        return analysisid;
    }

    public void setAnalysisid(Long analysisid) {
        this.analysisid = analysisid;
    }

    public String getAnlysisRegion() {
        return anlysisRegion;
    }

    public void setAnlysisRegion(String anlysisRegion) {
        this.anlysisRegion = anlysisRegion;
    }

    public String getAnalysisRegionName() {
        return analysisRegionName;
    }

    public void setAnalysisRegionName(String analysisRegionName) {
        this.analysisRegionName = analysisRegionName;
    }

    public String getProfileRegion() {
        return profileRegion;
    }

    public void setProfileRegion(String profileRegion) {
        this.profileRegion = profileRegion;
    }

    public String getProfileRegionName() {
        return profileRegionName;
    }

    public void setProfileRegionName(String profileRegionName) {
        this.profileRegionName = profileRegionName;
    }

    public String getPeril() {
        return Peril;
    }

    public void setPeril(String peril) {
        Peril = peril;
    }

    public BigDecimal getAal() {
        return aal;
    }

    public void setAal(BigDecimal aal) {
        this.aal = aal;
    }
}
