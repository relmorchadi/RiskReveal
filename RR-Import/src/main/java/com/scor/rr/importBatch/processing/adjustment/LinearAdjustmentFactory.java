package com.scor.rr.importBatch.processing.adjustment;

import com.scor.rr.domain.adjustments.LinearAdjustmentDefinition;

/**
 * Created by U002629 on 03/03/2016.
 */
public class LinearAdjustmentFactory implements AdjustmentFactory<LinearAdjustment, LinearAdjustmentDefinition> {
    @Override
    public LinearAdjustment buildAdjustment(LinearAdjustmentDefinition definition) {
        return new LinearAdjustment(definition);
    }
}
