package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class DefaultAdjustmentsInScopeViewDTO {
    private String workspaceContextCode;
    private Integer uwYear;

    private Long pltId;

    private String pltType;

    private int adjustmentNodeId;

    private String basisCode;

    private String basisShortName;

    private String categoryCode;

    private String adjustmentType;

    private Boolean capped;

    private Object factors;
}
