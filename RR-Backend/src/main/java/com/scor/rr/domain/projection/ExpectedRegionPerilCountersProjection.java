package com.scor.rr.domain.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ExpectedRegionPerilCountersProjection {

    @Value("#{target.workspaceId}")
    int getWorkspaceId();
    @Value("#{target.accumulatedPlts}")
    int getAccumulatedPlts();
    @Value("#{target.pricedPlts}")
    int getPricedPlts();
    @Value("#{target.publishedForPricingPlts}")
    int getPublishedForPricingPlts();

}
