package com.scor.rr.domain.dto.TargetBuild;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectStatistics {
    Integer regionPerils;
    Integer plts;
    Integer publishedForPricingCount;
    Integer finalPricing;
    Integer accumulatedPlts;

    Boolean importedFlag;
    Boolean publishedForPricingFlag;
    Boolean accumulatedFlag;
}
