package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmAllAnalysisProfileRegions {

    private Long analysisId;
    private String analysisRegion;
    private String analysisRegionName;
    private String profileRegion;
    private String profileRegionName;
    private String Peril;
    private BigDecimal aal;

}
