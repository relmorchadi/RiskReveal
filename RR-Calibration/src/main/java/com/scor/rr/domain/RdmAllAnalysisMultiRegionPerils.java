package com.scor.rr.domain;

import lombok.Data;

@Data
public class RdmAllAnalysisMultiRegionPerils {

    private Long rdmId;
    private String rdmName;
    private Long analysisId;
    private String ssRegion;
    private String ssPeril;
    private String ssRegionPeril;
    private String profileKey;
    private Long evCount;


}


