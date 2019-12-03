package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class AnalysisHeader {

    private Long analysisId;
    private String analysisName;
    private Long rdmId;
    private String rdmName;

}
