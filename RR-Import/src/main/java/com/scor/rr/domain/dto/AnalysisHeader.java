package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisHeader {

    private Long analysisId;
    private String analysisName;
    private Long rdmId;
    private String rdmName;

}
