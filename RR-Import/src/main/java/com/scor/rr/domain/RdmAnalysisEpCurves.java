package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmAnalysisEpCurves {

    private Long analysisId;
    private String finPerspCode;
    private String treatyLabelId;
    private String treatyLabel;
    private int ebpTypeCode;
    private Double loss;
    private int exceedanceProbabilty;
    private BigDecimal returnId;

}
