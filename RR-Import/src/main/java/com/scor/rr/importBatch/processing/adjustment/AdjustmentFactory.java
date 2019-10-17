package com.scor.rr.importBatch.processing.adjustment;

import com.scor.rr.domain.adjustments.AdjustmentDefinition;

/**
 * Created by U002629 on 03/03/2016.
 */
public interface AdjustmentFactory<T extends Adjustment, S extends AdjustmentDefinition> {
    T buildAdjustment(S definition);
}
