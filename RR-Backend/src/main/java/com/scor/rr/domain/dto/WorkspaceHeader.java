package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class WorkspaceHeader {
    Integer expectedRegionPerils;
    Integer expectedExposureSummaries;
    Integer qualifiedPLTs;
    Integer expectedPublishedForPricing;
    Integer expectedPriced;
    Integer expectedAccumulation;
    Boolean isFavorite;
}
