package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmAllAnalysisSummaryStats {

    private Long analysisId;
    private String finPerspCode;
    private int treatyLabelId;
    private String treatyLabel;
    private String treatyTyepCode;
    private String treatyType;
    private String treatyTag;
    private String occurrenceBasis;
    private int epTypeCode;
    private BigDecimal purePremium;
    private BigDecimal stdDev;
    private BigDecimal cov;

    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    public String getFinPerspCode() {
        return finPerspCode;
    }

    public void setFinPerspCode(String finPerspCode) {
        this.finPerspCode = finPerspCode;
    }

    public int getTreatyLabelId() {
        return treatyLabelId;
    }

    public void setTreatyLabelId(int treatyLabelId) {
        this.treatyLabelId = treatyLabelId;
    }

    public String getTreatyLabel() {
        return treatyLabel;
    }

    public void setTreatyLabel(String treatyLabel) {
        this.treatyLabel = treatyLabel;
    }

    public String getTreatyTyepCode() {
        return treatyTyepCode;
    }

    public void setTreatyTyepCode(String treatyTyepCode) {
        this.treatyTyepCode = treatyTyepCode;
    }

    public String getTreatyType() {
        return treatyType;
    }

    public void setTreatyType(String treatyType) {
        this.treatyType = treatyType;
    }

    public String getTreatyTag() {
        return treatyTag;
    }

    public void setTreatyTag(String treatyTag) {
        this.treatyTag = treatyTag;
    }

    public String getOccurrenceBasis() {
        return occurrenceBasis;
    }

    public void setOccurrenceBasis(String occurrenceBasis) {
        this.occurrenceBasis = occurrenceBasis;
    }

    public int getEpTypeCode() {
        return epTypeCode;
    }

    public void setEpTypeCode(int epTypeCode) {
        this.epTypeCode = epTypeCode;
    }

    public BigDecimal getPurePremium() {
        return purePremium;
    }

    public void setPurePremium(BigDecimal purePremium) {
        this.purePremium = purePremium;
    }

    public BigDecimal getStdDev() {
        return stdDev;
    }

    public void setStdDev(BigDecimal stdDev) {
        this.stdDev = stdDev;
    }

    public BigDecimal getCov() {
        return cov;
    }

    public void setCov(BigDecimal cov) {
        this.cov = cov;
    }
}
