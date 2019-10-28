package com.scor.rr.domain;

import lombok.Data;

@Data
public class RdmAllAnalysisTreatyStructure {
    private int analysisId;
    private int treatyId;
    private String treatyNum;
    private String treatyName;
    private String treatyType;
    private double riskLimit;
    private double occurenceLimit;
    private double attachmentPoint;
    private String lob;
    private String cedant;
    private double pctCovered;
    private double pctPlaced;
    private double pctRiShared;
    private double pctRetention;
    private int noofReinstatements;
    private int InuringPriority;
    private String ccyCode;
    private String attachementBasis;
    private String exposureLevel;

    public int getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(int analysisId) {
        this.analysisId = analysisId;
    }

    public int getTreatyId() {
        return treatyId;
    }

    public void setTreatyId(int treatyId) {
        this.treatyId = treatyId;
    }

    public String getTreatyNum() {
        return treatyNum;
    }

    public void setTreatyNum(String treatyNum) {
        this.treatyNum = treatyNum;
    }

    public String getTreatyName() {
        return treatyName;
    }

    public void setTreatyName(String treatyName) {
        this.treatyName = treatyName;
    }

    public String getTreatyType() {
        return treatyType;
    }

    public void setTreatyType(String treatyType) {
        this.treatyType = treatyType;
    }

    public double getRiskLimit() {
        return riskLimit;
    }

    public void setRiskLimit(double riskLimit) {
        this.riskLimit = riskLimit;
    }

    public double getOccurenceLimit() {
        return occurenceLimit;
    }

    public void setOccurenceLimit(double occurenceLimit) {
        this.occurenceLimit = occurenceLimit;
    }

    public double getAttachmentPoint() {
        return attachmentPoint;
    }

    public void setAttachmentPoint(double attachmentPoint) {
        this.attachmentPoint = attachmentPoint;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public String getCedant() {
        return cedant;
    }

    public void setCedant(String cedant) {
        this.cedant = cedant;
    }

    public double getPctCovered() {
        return pctCovered;
    }

    public void setPctCovered(double pctCovered) {
        this.pctCovered = pctCovered;
    }

    public double getPctPlaced() {
        return pctPlaced;
    }

    public void setPctPlaced(double pctPlaced) {
        this.pctPlaced = pctPlaced;
    }

    public double getPctRiShared() {
        return pctRiShared;
    }

    public void setPctRiShared(double pctRiShared) {
        this.pctRiShared = pctRiShared;
    }

    public double getPctRetention() {
        return pctRetention;
    }

    public void setPctRetention(double pctRetention) {
        this.pctRetention = pctRetention;
    }

    public int getNoofReinstatements() {
        return noofReinstatements;
    }

    public void setNoofReinstatements(int noofReinstatements) {
        this.noofReinstatements = noofReinstatements;
    }

    public int getInuringPriority() {
        return InuringPriority;
    }

    public void setInuringPriority(int inuringPriority) {
        InuringPriority = inuringPriority;
    }

    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    public String getAttachementBasis() {
        return attachementBasis;
    }

    public void setAttachementBasis(String attachementBasis) {
        this.attachementBasis = attachementBasis;
    }

    public String getExposureLevel() {
        return exposureLevel;
    }

    public void setExposureLevel(String exposureLevel) {
        this.exposureLevel = exposureLevel;
    }
}
