package com.scor.rr.importBatch.processing.adjustment;

import com.scor.rr.domain.adjustments.DummyAdjustmentDefinition;

/**
 * Created by U002629 on 07/03/2016.
 */
public class DummyAdjustmentFactory implements AdjustmentFactory<DummyAdjustment, DummyAdjustmentDefinition> {
    @Override
    public DummyAdjustment buildAdjustment(DummyAdjustmentDefinition definition) {
        return new DummyAdjustment(definition);
    }
}
