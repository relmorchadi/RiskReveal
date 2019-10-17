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
}
