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

}
