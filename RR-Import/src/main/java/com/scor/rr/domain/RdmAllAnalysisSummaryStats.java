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
    private Double purePremium;
    private Double stdDev;
    private Double cov;

}
